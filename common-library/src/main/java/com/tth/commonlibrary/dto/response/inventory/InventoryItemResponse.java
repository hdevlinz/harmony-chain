package com.tth.commonlibrary.dto.response.inventory;

import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemResponse {

    private String id;

    private Float quantity;

    private ProductListResponse product;

}
