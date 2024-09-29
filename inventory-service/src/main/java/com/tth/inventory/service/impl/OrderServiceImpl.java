package com.tth.inventory.service.impl;

import com.tth.order.dto.PageResponse;
import com.tth.order.dto.request.OrderDetailsRequest;
import com.tth.order.dto.request.OrderRequest;
import com.tth.order.dto.response.OrderResponse;
import com.tth.order.dto.response.product.ProductListResponse;
import com.tth.order.dto.response.user.User;
import com.tth.order.enums.ErrorCode;
import com.tth.order.enums.OrderStatus;
import com.tth.order.enums.OrderType;
import com.tth.order.exception.AppException;
import com.tth.order.mapper.OrderMapper;
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

    private final OrderMapper orderMapper;
    private final ProductClient productClient;
    private final TaxRepository taxRepository;
    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final InventoryDetailsRepository inventoryDetailsRepository;

    @Override
    public OrderResponse findByOrderNumber(String orderNumber) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        return this.orderRepository.findByOrderNumberAndUserId(orderNumber, user.getName())
                .map(this.orderMapper::toOrderResponse)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    }

    @Override
    public void checkout(OrderRequest orderRequest, User usert) {
        Authentication user;
        if (usert == null) {
            user = SecurityContextHolder.getContext().getAuthentication();
        } else {
            user = null;
        }

        if (orderRequest.getType() == OrderType.OUTBOUND) {
            // Tập hợp tất cả các productId từ danh sách OrderDetails
            Set<String> productIds = orderRequest.getOrderDetails().stream()
                    .map(OrderDetailsRequest::getProductId).collect(Collectors.toSet());

            // Gọi một lần để lấy thông tin của tất cả các sản phẩm trong productIds
            List<ProductListResponse> products = this.productClient.getProductsInBatch(productIds).getResult();

            // Tạo một map để tra cứu sản phẩm nhanh chóng
            Map<String, ProductListResponse> productMap = products.stream()
                    .collect(Collectors.toMap(ProductListResponse::getId, product -> product));

            // Nhóm các sản phẩm theo nhà cung cấp
            Map<String, Set<OrderDetailsRequest>> groupedBySupplier = orderRequest.getOrderDetails().stream()
                    .collect(Collectors.groupingBy(odr -> productMap.get(odr.getProductId()).getSupplier().getId(), Collectors.toSet()));

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
                totalAmount[0] = this.getTotalAmountOfOrder(order, orderDetailsList, productMap);
            });

            // Tạo hóa đơn cho đơn hàng
            this.createInvoice(usert != null ? usert.getId() : user.getName(), order, orderRequest, totalAmount[0]);
        }
    }

    private BigDecimal[] getTotalAmountOfOrder(Order order, Set<OrderDetailsRequest> orderDetailsRequests, Map<String, ProductListResponse> productMap) {
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};

        // Duyệt danh sách các sản phẩm trong đơn hàng
        orderDetailsRequests.forEach(odr -> {
            if (odr.getQuantity() > 0) {
                // Tìm kiếm và kiểm tra tồn kho nào còn hàng không
                InventoryDetails inventoryDetails = this.checkQuantityOfInventory(productMap.get(odr.getProductId()), odr.getQuantity());

                // Nếu có hàng trong kho và đủ số lượng khách yêu cầu thì cập nhật lại số lượng tồn kho
                inventoryDetails.setQuantity(inventoryDetails.getQuantity() - odr.getQuantity());
                this.inventoryDetailsRepository.save(inventoryDetails);

                // Tạo chi tiết đơn hàng
                this.createOrderDetails(order, productMap.get(odr.getProductId()), inventoryDetails, odr);

                totalAmount[0] = totalAmount[0].add(productMap.get(odr.getProductId()).getPrice().multiply(BigDecimal.valueOf(odr.getQuantity())));
            }
        });

        return totalAmount;
    }

    private InventoryDetails checkQuantityOfInventory(ProductListResponse product, Float orderQuantity) {
        List<InventoryDetails> currentInventoryDetails = this.inventoryDetailsRepository.findByProductId(product.getId());

        for (InventoryDetails details : currentInventoryDetails) {
            if (details.getQuantity() >= orderQuantity) {
                return details;
            }
        }

        throw new IllegalArgumentException("Không đủ hàng trong kho cho sản phẩm: " + product.getName());
    }

    @Override
    public void checkin(OrderRequest orderRequest, User usert) {
        Authentication user;
        if (usert == null) {
            user = SecurityContextHolder.getContext().getAuthentication();
        } else {
            user = null;
        }

        if (orderRequest.getType() == OrderType.INBOUND) {
            // Tìm kiếm tồn kho cần nhập hàng, nếu không tìm thấy thì báo lỗi
            Inventory inventory = this.inventoryRepository.findById(orderRequest.getInventoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));

            // Tập hợp tất cả các productId từ danh sách InventoryDetails
            Set<String> productIds = orderRequest.getOrderDetails().stream()
                    .map(OrderDetailsRequest::getProductId).collect(Collectors.toSet());

            // Gọi một lần để lấy thông tin của tất cả các sản phẩm trong productIds
            List<ProductListResponse> products = this.productClient.getProductsInBatch(productIds).getResult();

            // Tạo một map để tra cứu sản phẩm nhanh chóng
            Map<String, ProductListResponse> productMap = products.stream()
                    .collect(Collectors.toMap(ProductListResponse::getId, product -> product));

            // Nhóm các sản phẩm theo nhà cung cấp
            Map<String, Set<OrderDetailsRequest>> groupedBySupplier = orderRequest.getOrderDetails().stream()
                    .collect(Collectors.groupingBy(odr -> productMap.get(odr.getProductId()).getSupplier().getId(), Collectors.toSet()));

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
                this.checkCapacityOfWarehouse(inventory, orderRequest.getOrderDetails());

                // Nếu không vượt quá sức chứa thì tính toán tổng giá trị của đơn hàng
                totalAmount[0] = this.getTotalAmountOfOrder(inventory, order, orderRequest.getOrderDetails(), productMap);
            });

            // Tạo hóa đơn cho đơn hàng
            this.createInvoice(usert != null ? usert.getId() : user.getName(), order, orderRequest, totalAmount[0]);
        }
    }

    private void checkCapacityOfWarehouse(Inventory inventory, Set<OrderDetailsRequest> orderDetailsRequests) {
        // Tính tổng số lượng sản phẩm hiện tại của tất cả tồn kho trong kho hàng
        Float totalCurrentQuantity = this.inventoryDetailsRepository.getTotalQuantityByWarehouseId(inventory.getWarehouse().getId());

        // Tính tổng số lượng sản phẩm trong đơn hàng
        Float totalOrderQuantity = orderDetailsRequests.stream()
                .map(OrderDetailsRequest::getQuantity)
                .reduce(0F, Float::sum);

        // Tính tổng số lượng sản phẩm sau khi nhập hàng
        float totalQuantity = totalCurrentQuantity + totalOrderQuantity;

        // Kiểm tra tổng số lượng sản phẩm vượt quá sức chứa của kho hàng không
        if (totalQuantity > inventory.getWarehouse().getCapacity()) {
            throw new IllegalArgumentException("Số lượng sản phẩm vượt quá sức chứa của kho " + inventory.getWarehouse().getName());
        }
    }

    private BigDecimal[] getTotalAmountOfOrder(Inventory inventory, Order order, Set<OrderDetailsRequest> orderDetailsRequests, Map<String, ProductListResponse> productMap) {
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};

        // Duyệt danh sách các sản phẩm trong đơn hàng
        orderDetailsRequests.forEach(odr -> {
            if (odr.getQuantity() > 0) {
                // Tìm tồn kho hiện tại của sản phẩm
                InventoryDetails inventoryDetails = this.inventoryDetailsRepository
                        .findByInventoryIdAndProductId(inventory.getId(), odr.getProductId())
                        .orElseGet(() -> {
                            InventoryDetails newInventoryDetails = InventoryDetails.builder()
                                    .inventory(inventory)
                                    .productId(odr.getProductId())
                                    .quantity(0F)
                                    .build();
                            this.inventoryDetailsRepository.save(newInventoryDetails);
                            return newInventoryDetails;
                        });

                // Sau khi tìm hoặc tạo mới tồn kho thì cập nhật lại số lượng tồn kho
                inventoryDetails.setQuantity(inventoryDetails.getQuantity() + odr.getQuantity());
                this.inventoryDetailsRepository.save(inventoryDetails);

                // Tạo chi tiết đơn hàng
                this.createOrderDetails(order, productMap.get(odr.getProductId()), inventoryDetails, odr);

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

        order.getOrderDetails().forEach(od -> {
            InventoryDetails inventoryDetails = od.getInventoryDetails();
            inventoryDetails.setQuantity(inventoryDetails.getQuantity() + od.getQuantity());
            this.inventoryDetailsRepository.save(inventoryDetails);
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

        switch (orderStatus) {
            case CANCELLED:
            case RETURNED:
                if (order.getType() == OrderType.OUTBOUND) {
                    order.getOrderDetails().forEach(od -> {
                        InventoryDetails inventoryDetails = od.getInventoryDetails();
                        inventoryDetails.setQuantity(inventoryDetails.getQuantity() + od.getQuantity());
                        this.inventoryDetailsRepository.save(inventoryDetails);
                    });
                } else if (order.getType() == OrderType.INBOUND) {
                    order.getOrderDetails().forEach(od -> {
                        InventoryDetails inventoryDetails = od.getInventoryDetails();
                        inventoryDetails.setQuantity(inventoryDetails.getQuantity() - od.getQuantity());
                        this.inventoryDetailsRepository.save(inventoryDetails);
                    });
                }
                break;
        }

        order.setStatus(orderStatus);
        this.orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> findRecentlyOrders() {
        return this.orderRepository.findRecentOrders().stream().map(this.orderMapper::toOrderResponse).collect(Collectors.toList());
    }

    @Override
    public PageResponse<Order> findAllByParams(Map<String, String> params, int page, int size) {
        Specification<Order> specification = OrderSpecification.filterByParams(params);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Order> orders = this.orderRepository.findAll(specification, pageable);

        return PageResponse.of(orders);
    }

    @Override
    public PageResponse<Order> findByDeliveryScheduleId(String deliveryScheduleId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Order> orders = this.orderRepository.findByDeliveryScheduleId(deliveryScheduleId, pageable);

        return PageResponse.of(orders);
    }

    @Override
    public PageResponse<OrderResponse> findAllOrderOfAuthenticated(Map<String, String> params, int page, int size) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        params.put("userId", user.getName());

        Specification<Order> specification = OrderSpecification.filterByParams(params);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<OrderResponse> invoices = this.orderRepository.findAll(specification, pageable)
                .map(this.orderMapper::toOrderResponse);

        return PageResponse.of(invoices);
    }

    @Override
    public PageResponse<Order> findAllBySupplierId(String supplierId, Map<String, String> params, int page, int size) {
        Specification<Order> specification = OrderSpecification.filterBySupplierId(supplierId, params);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Order> orders = this.orderRepository.findAll(specification, pageable);

        return PageResponse.of(orders);
    }

    private void createOrderDetails(Order order, ProductListResponse product, InventoryDetails inventoryDetails, OrderDetailsRequest odr) {
        OrderDetails orderDetails = OrderDetails.builder()
                .order(order)
                .productId(product.getId())
                .inventoryDetails(inventoryDetails)
                .quantity(odr.getQuantity())
                .unitPrice(product.getPrice())
                .build();
        this.orderDetailsRepository.save(orderDetails);
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
