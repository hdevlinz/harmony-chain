package com.tth.inventory.mapper.helper;

import com.tth.commonlibrary.dto.response.inventory.InventoryItemResponse;
import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.inventory.entity.Inventory;
import com.tth.inventory.entity.InventoryItem;
import com.tth.inventory.entity.Warehouse;
import com.tth.inventory.repository.InventoryRepository;
import com.tth.inventory.repository.WarehouseRepository;
import com.tth.inventory.repository.httpclient.ProductClient;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MappingHelper {

    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductClient productClient;

    @Named("getProductById")
    public ProductListResponse getProductById(String productId) {
        Set<String> productIds = Set.of(productId);

        return this.productClient.listProductsInBatch(productIds).getResult().getFirst();
    }

    @Named("getInventoryById")
    public Inventory getInventoryById(String inventoryId) {
        return this.inventoryRepository.findById(inventoryId).orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));
    }

    @Named("getWarehouseById")
    public Warehouse getWarehouseById(String warehouseId) {
        return this.warehouseRepository.findById(warehouseId).orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_FOUND));
    }

    @Named("mapInventoryItemSetToResponse")
    public Set<InventoryItemResponse> mapInventoryItemSetToResponse(Set<InventoryItem> inventoryDetails) {
        if (inventoryDetails == null) {
            return null;
        }

        // Tập hợp tất cả các productId từ danh sách InventoryDetails
        Set<String> productIds = inventoryDetails.stream().map(InventoryItem::getProductId).collect(Collectors.toSet());

        // Gọi một lần để lấy thông tin của tất cả các sản phẩm trong productIds
        List<ProductListResponse> products = this.productClient.listProductsInBatch(productIds).getResult();

        // Tạo một map để tra cứu sản phẩm nhanh chóng
        Map<String, ProductListResponse> productMap = products.stream()
                .collect(Collectors.toMap(ProductListResponse::getId, product -> product));

        // Duyệt qua từng InventoryDetails và ánh xạ đến sản phẩm tương ứng từ productMap
        return inventoryDetails.stream().map(inventoryDetail -> {
            InventoryItemResponse response = new InventoryItemResponse();
            response.setQuantity(inventoryDetail.getQuantity());
            response.setProduct(productMap.get(inventoryDetail.getProductId())); // Ánh xạ sản phẩm từ productMap
            return response;
        }).collect(Collectors.toSet());
    }

}
