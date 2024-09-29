package com.tth.order.mapper.helper;

import com.tth.commonlibrary.dto.response.inventory.InventoryDetailsResponse;
import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.order.entity.InventoryDetails;
import com.tth.order.entity.Warehouse;
import com.tth.order.repository.WarehouseRepository;
import com.tth.order.repository.httpclient.ProductClient;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InventoryHelper {

    private final WarehouseRepository warehouseRepository;
    private final ProductClient productClient;

    @Named("findWarehouseById")
    public Warehouse findWarehouseById(String warehouseId) {
        return this.warehouseRepository.findById(warehouseId).orElseThrow(() -> new AppException(ErrorCode.WAREHOUSE_NOT_FOUND));
    }

    @Named("mapInventoryDetailsSetToResponse")
    public Set<InventoryDetailsResponse> mapInventoryDetailsSetToResponse(Set<InventoryDetails> inventoryDetails) {
        if (inventoryDetails == null) {
            return null;
        }

        // Tập hợp tất cả các productId từ danh sách InventoryDetails
        Set<String> productIds = inventoryDetails.stream().map(InventoryDetails::getProductId).collect(Collectors.toSet());

        // Gọi một lần để lấy thông tin của tất cả các sản phẩm trong productIds
        List<ProductListResponse> products = this.productClient.getProductsInBatch(productIds).getResult();

        // Tạo một map để tra cứu sản phẩm nhanh chóng
        Map<String, ProductListResponse> productMap = products.stream()
                .collect(Collectors.toMap(ProductListResponse::getId, product -> product));

        // Duyệt qua từng InventoryDetails và ánh xạ đến sản phẩm tương ứng từ productMap
        return inventoryDetails.stream().map(inventoryDetail -> {
            InventoryDetailsResponse response = new InventoryDetailsResponse();
            response.setQuantity(inventoryDetail.getQuantity());
            response.setProduct(productMap.get(inventoryDetail.getProductId())); // Ánh xạ sản phẩm từ productMap
            return response;
        }).collect(Collectors.toSet());
    }

    @Named("mapInventoryDetailsToResponse")
    public ProductListResponse mapInventoryDetailsToResponse(String productId) {
        if (productId == null) {
            return null;
        }

        Set<String> productIds = new HashSet<>();
        productIds.add(productId);

        return this.productClient.getProductsInBatch(productIds).getResult().getFirst();
    }

}
