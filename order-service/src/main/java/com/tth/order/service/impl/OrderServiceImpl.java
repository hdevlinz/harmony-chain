package com.tth.order.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.request.inventory.InventoryItemRequestCreate;
import com.tth.commonlibrary.dto.request.inventory.InventoryItemRequestUpdate;
import com.tth.commonlibrary.dto.request.order.OrderItemRequest;
import com.tth.commonlibrary.dto.request.order.OrderRequest;
import com.tth.commonlibrary.dto.response.identity.user.UserResponse;
import com.tth.commonlibrary.dto.response.inventory.InventoryItemResponse;
import com.tth.commonlibrary.dto.response.inventory.InventoryResponse;
import com.tth.commonlibrary.dto.response.order.OrderResponse;
import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.enums.OrderStatus;
import com.tth.commonlibrary.enums.OrderType;
import com.tth.commonlibrary.exception.AppException;
import com.tth.order.entity.Invoice;
import com.tth.order.entity.Order;
import com.tth.order.entity.OrderItem;
import com.tth.order.entity.Tax;
import com.tth.order.mapper.OrderMapper;
import com.tth.order.repository.InvoiceRepository;
import com.tth.order.repository.OrderItemRepository;
import com.tth.order.repository.OrderRepository;
import com.tth.order.repository.TaxRepository;
import com.tth.order.repository.httpclient.InventoryClient;
import com.tth.order.repository.httpclient.InventoryItemClient;
import com.tth.order.repository.httpclient.ProductClient;
import com.tth.order.service.OrderService;
import com.tth.order.service.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InvoiceRepository invoiceRepository;
    private final TaxRepository taxRepository;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final InventoryItemClient inventoryItemClient;

    @Override
    public OrderResponse findByOrderNumber(String orderNumber) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        return this.orderRepository.findByOrderNumberAndUserId(orderNumber, user.getName())
                .map(this.orderMapper::toOrderResponse)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    }

    @Override
    public void checkout(OrderRequest orderRequest, UserResponse usert) {
        Authentication user;
        if (usert == null) {
            user = SecurityContextHolder.getContext().getAuthentication();
        } else {
            user = null;
        }

        if (orderRequest.getType() == OrderType.OUTBOUND) {
            // Lấy danh sách các sản phẩm trong đơn hàng
            Set<String> productIds = orderRequest.getOrderItems().stream()
                    .map(OrderItemRequest::getProductId).collect(Collectors.toSet());
            List<ProductListResponse> products = this.productClient.listProductsInBatch(productIds).getResult();
            Map<String, ProductListResponse> productsMap = products.stream()
                    .collect(Collectors.toMap(ProductListResponse::getId, product -> product));

            // Nhóm các sản phẩm theo nhà cung cấp
            Map<String, Set<OrderItemRequest>> groupedBySupplier = orderRequest.getOrderItems().stream()
                    .collect(Collectors.groupingBy(odr -> {
                        ProductListResponse product = productsMap.get(odr.getProductId());
                        if (product == null) {
                            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
                        }

                        return product.getSupplier().getId();
                    }, Collectors.toSet()));

            // Tạo đơn hàng mới
            Order order = Order.builder()
                    .userId(usert != null ? usert.getId() : user.getName())
                    .type(orderRequest.getType())
                    .build();
            if (orderRequest.getStatus() != null) {
                order.setStatus(orderRequest.getStatus());
            }
            this.orderRepository.save(order);

            // Tạo đơn hàng cho mỗi nhà cung cấp
            final BigDecimal[][] totalAmount = {{BigDecimal.ZERO}};
            groupedBySupplier.forEach((supplier, orderDetailsList) -> {
                // Tính toán tổng giá trị của đơn hàng
                totalAmount[0] = this.getTotalAmountOfOrder(order, orderDetailsList, productsMap);
            });

            // Tạo hóa đơn cho đơn hàng
            this.createInvoice(usert != null ? usert.getId() : user.getName(), order, orderRequest, totalAmount[0]);
        }
    }

    private BigDecimal[] getTotalAmountOfOrder(Order order, Set<OrderItemRequest> orderDetailsRequests, Map<String, ProductListResponse> productMap) {
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};

        // Duyệt danh sách các sản phẩm trong đơn hàng
        orderDetailsRequests.forEach(odr -> {
            if (odr.getQuantity() > 0) {
                // Tìm kiếm và kiểm tra tồn kho nào còn hàng không
                InventoryItemResponse inventoryItem = this.checkQuantityOfInventory(productMap.get(odr.getProductId()), odr.getQuantity());

                // Nếu có hàng trong kho và đủ số lượng khách yêu cầu thì cập nhật lại số lượng tồn kho
                inventoryItem.setQuantity(inventoryItem.getQuantity() - odr.getQuantity());
                InventoryItemRequestUpdate request = InventoryItemRequestUpdate.builder()
                        .quantity(inventoryItem.getQuantity())
                        .build();
                this.inventoryItemClient.updateInventoryItem(inventoryItem.getId(), request);

                // Tạo chi tiết đơn hàng
                this.createOrderItem(order, productMap.get(odr.getProductId()), inventoryItem, odr);

                totalAmount[0] = totalAmount[0].add(productMap.get(odr.getProductId()).getPrice().multiply(BigDecimal.valueOf(odr.getQuantity())));
            }
        });

        return totalAmount;
    }

    private InventoryItemResponse checkQuantityOfInventory(ProductListResponse product, Float orderQuantity) {
        List<InventoryItemResponse> inventoryItemList = this.inventoryItemClient.listInventoryItems(Map.of("product", product.getId())).getResult();

        for (InventoryItemResponse details : inventoryItemList) {
            if (details.getQuantity() >= orderQuantity) {
                return details;
            }
        }

        throw new IllegalArgumentException("Không đủ hàng trong kho cho sản phẩm: " + product.getName());
    }

    @Override
    public void checkin(OrderRequest orderRequest, UserResponse usert) {
        Authentication user;
        if (usert == null) {
            user = SecurityContextHolder.getContext().getAuthentication();
        } else {
            user = null;
        }

        if (orderRequest.getType() == OrderType.INBOUND) {
            // Tìm kiếm tồn kho cần nhập hàng, nếu không tìm thấy thì báo lỗi
            InventoryResponse inventory = this.inventoryClient.getInventory(orderRequest.getInventoryId()).getResult();

            // Lấy danh sách các sản phẩm trong đơn hàng
            Set<String> productIds = orderRequest.getOrderItems().stream()
                    .map(OrderItemRequest::getProductId).collect(Collectors.toSet());
            List<ProductListResponse> products = this.productClient.listProductsInBatch(productIds).getResult();
            Map<String, ProductListResponse> productsMap = products.stream()
                    .collect(Collectors.toMap(ProductListResponse::getId, product -> product));

            // Nhóm các sản phẩm theo nhà cung cấp
            Map<String, Set<OrderItemRequest>> groupedBySupplier = orderRequest.getOrderItems().stream()
                    .collect(Collectors.groupingBy(odr -> productsMap.get(odr.getProductId()).getSupplier().getId(), Collectors.toSet()));

            // Tạo đơn hàng mới
            Order order = Order.builder()
                    .userId(usert != null ? usert.getId() : user.getName())
                    .type(orderRequest.getType())
                    .build();
            if (orderRequest.getStatus() != null) {
                order.setStatus(orderRequest.getStatus());
            }
            this.orderRepository.save(order);

            // Tạo đơn hàng cho mỗi nhà cung cấp
            final BigDecimal[][] totalAmount = {{BigDecimal.ZERO}};
            groupedBySupplier.forEach((supplier, orderDetailsList) -> {
                this.checkCapacityOfWarehouse(inventory, orderRequest.getOrderItems());

                // Nếu không vượt quá sức chứa thì tính toán tổng giá trị của đơn hàng
                totalAmount[0] = this.getTotalAmountOfOrder(inventory, order, orderRequest.getOrderItems(), productsMap);
            });

            // Tạo hóa đơn cho đơn hàng
            this.createInvoice(usert != null ? usert.getId() : user.getName(), order, orderRequest, totalAmount[0]);
        }
    }

    private void checkCapacityOfWarehouse(InventoryResponse inventory, Set<OrderItemRequest> orderDetailsRequests) {
        // Tính tổng số lượng sản phẩm hiện tại của tất cả tồn kho trong kho hàng
        Float totalCurrentQuantity = this.inventoryItemClient.getTotalQuantityByWarehouseId(inventory.getWarehouse().getId())
                .getResult().get("totalQuantity");

        // Tính tổng số lượng sản phẩm trong đơn hàng
        Float totalOrderQuantity = orderDetailsRequests.stream()
                .map(OrderItemRequest::getQuantity)
                .reduce(0F, Float::sum);

        // Tính tổng số lượng sản phẩm sau khi nhập hàng
        float totalQuantity = totalCurrentQuantity + totalOrderQuantity;

        // Kiểm tra tổng số lượng sản phẩm vượt quá sức chứa của kho hàng không
        if (totalQuantity > inventory.getWarehouse().getCapacity()) {
            throw new IllegalArgumentException("Số lượng sản phẩm vượt quá sức chứa của kho " + inventory.getWarehouse().getName());
        }
    }

    private BigDecimal[] getTotalAmountOfOrder(
            InventoryResponse inventory,
            Order order,
            Set<OrderItemRequest> orderDetailsRequests,
            Map<String, ProductListResponse> productMap
    ) {
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};

        // Duyệt danh sách các sản phẩm trong đơn hàng
        orderDetailsRequests.forEach(odr -> {
            if (odr.getQuantity() > 0) {
                // Tìm tồn kho hiện tại của sản phẩm
                InventoryItemResponse inventoryItem = this.inventoryItemClient.listInventoryItems(
                        Map.of("inventory", inventory.getId(), "product", odr.getProductId())
                ).getResult().getFirst();

                if (inventoryItem == null) {
                    InventoryItemRequestCreate request = InventoryItemRequestCreate.builder()
                            .inventoryId(inventory.getId())
                            .productId(odr.getProductId())
                            .quantity(0F)
                            .build();
                    inventoryItem = this.inventoryItemClient.createInventoryItem(request).getResult();
                }

                // Sau khi tìm hoặc tạo mới tồn kho thì cập nhật lại số lượng tồn kho
                inventoryItem.setQuantity(inventoryItem.getQuantity() + odr.getQuantity());
                InventoryItemRequestUpdate request = InventoryItemRequestUpdate.builder()
                        .quantity(inventoryItem.getQuantity())
                        .build();
                this.inventoryItemClient.updateInventoryItem(inventoryItem.getId(), request);

                // Tạo chi tiết đơn hàng
                this.createOrderItem(order, productMap.get(odr.getProductId()), inventoryItem, odr);

                totalAmount[0] = totalAmount[0].add(productMap.get(odr.getProductId()).getPrice().multiply(BigDecimal.valueOf(odr.getQuantity())));
            }
        });

        return totalAmount;
    }

    @Override
    public void cancelOrder(String orderNumber) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        Order order = this.orderRepository.findByOrderNumberAndUserId(orderNumber, user.getName())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        switch (order.getStatus()) {
            case CANCELLED:
                throw new IllegalStateException("Đơn hàng đã bị hủy");
            case CONFIRMED:
            case SHIPPED:
            case DELIVERED:
                throw new IllegalStateException("Đơn hàng không thể hủy vì đã được xác nhận");
            default:
                break;
        }

        order.setStatus(OrderStatus.CANCELLED);
        this.orderRepository.save(order);

        List<String> inventoryItemIds = order.getOrderItems().stream().map(OrderItem::getInventoryItemId).toList();
        List<InventoryItemResponse> inventoryItems = this.inventoryItemClient.listInventoryItemsInBatch(inventoryItemIds).getResult();
        Map<String, InventoryItemResponse> inventoryItemMap = inventoryItems.stream()
                .collect(Collectors.toMap(InventoryItemResponse::getId, inventoryItem -> inventoryItem));

        order.getOrderItems().forEach(od -> {
            InventoryItemResponse inventoryItem = inventoryItemMap.get(od.getInventoryItemId());
            inventoryItem.setQuantity(inventoryItem.getQuantity() + od.getQuantity());

            InventoryItemRequestUpdate request = InventoryItemRequestUpdate.builder()
                    .quantity(inventoryItem.getQuantity())
                    .build();
            this.inventoryItemClient.updateInventoryItem(inventoryItem.getId(), request);
        });

        this.invoiceRepository.findByOrderId(orderNumber).ifPresent(invoice -> {
            order.setInvoice(null);
            this.invoiceRepository.delete(invoice);
        });
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'SHIPPER', 'SUPPLIER', 'DISTRIBUTOR', 'MANUFACTURER')")
    public void updateOrderStatus(String orderNumber, String status) {
        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status.toUpperCase(Locale.getDefault()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Trạng thái đơn hàng không hợp lệ");
        }

        Order order = this.orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Đơn hàng đã bị hủy");
        }

        if (order.getStatus() == orderStatus) {
            throw new IllegalStateException("Đơn hàng đã ở trạng thái " + orderStatus.getDisplayName());
        }

        List<String> inventoryItemIds = order.getOrderItems().stream().map(OrderItem::getInventoryItemId).toList();
        List<InventoryItemResponse> inventoryItems = this.inventoryItemClient.listInventoryItemsInBatch(inventoryItemIds).getResult();
        Map<String, InventoryItemResponse> inventoryItemMap = inventoryItems.stream()
                .collect(Collectors.toMap(InventoryItemResponse::getId, inventoryItem -> inventoryItem));
        switch (orderStatus) {
            case CANCELLED:
            case RETURNED:
                if (order.getType() == OrderType.OUTBOUND) {
                    order.getOrderItems().forEach(od -> {
                        InventoryItemResponse inventoryItem = inventoryItemMap.get(od.getInventoryItemId());
                        inventoryItem.setQuantity(inventoryItem.getQuantity() + od.getQuantity());

                        InventoryItemRequestUpdate request = InventoryItemRequestUpdate.builder()
                                .quantity(inventoryItem.getQuantity())
                                .build();
                        this.inventoryItemClient.updateInventoryItem(inventoryItem.getId(), request);
                    });
                } else if (order.getType() == OrderType.INBOUND) {
                    order.getOrderItems().forEach(od -> {
                        InventoryItemResponse inventoryItem = inventoryItemMap.get(od.getInventoryItemId());
                        inventoryItem.setQuantity(inventoryItem.getQuantity() - od.getQuantity());

                        InventoryItemRequestUpdate request = InventoryItemRequestUpdate.builder()
                                .quantity(inventoryItem.getQuantity())
                                .build();
                        this.inventoryItemClient.updateInventoryItem(inventoryItem.getId(), request);
                    });
                }
                break;
        }

        order.setStatus(orderStatus);
        this.orderRepository.save(order);
    }

    @Override
    public PageResponse<OrderResponse> findAllOfAuthenticated(Map<String, String> params, int page, int size) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        params.put("user", user.getName());

        Specification<Order> specification = OrderSpecification.filter(params);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<OrderResponse> orders = this.orderRepository.findAll(specification, pageable)
                .map(this.orderMapper::toOrderResponse);

        return PageResponse.of(orders);
    }

    private void createOrderItem(Order order, ProductListResponse product, InventoryItemResponse inventoryItem, OrderItemRequest odr) {
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .productId(product.getId())
                .inventoryItemId(inventoryItem.getId())
                .quantity(odr.getQuantity())
                .unitPrice(product.getPrice())
                .build();
        this.orderItemRepository.save(orderItem);
    }

    private void createInvoice(String userId, Order order, OrderRequest orderRequest, BigDecimal[] totalAmount) {
        if (totalAmount[0].compareTo(BigDecimal.ZERO) < 1) {
            throw new IllegalArgumentException("Không có sản phẩm nào trong đơn hàng");
        }

        Tax tax = this.taxRepository.findByRegion("VN").orElseGet(() -> {
            Tax newTax = Tax.builder()
                    .region("VN")
                    .rate(BigDecimal.valueOf(0.1))
                    .build();
            this.taxRepository.save(newTax);
            return newTax;
        });

        Invoice invoice = Invoice.builder()
                .userId(userId)
                .order(order)
                .tax(tax)
                .totalAmount(totalAmount[0].add(totalAmount[0].multiply(tax.getRate())))
                .build();
        invoice.setCreatedAt(orderRequest.getCreatedAt());
        if (orderRequest.getPaid() != null) {
            invoice.setPaid(orderRequest.getPaid());
        }
        this.invoiceRepository.save(invoice);
    }

}
