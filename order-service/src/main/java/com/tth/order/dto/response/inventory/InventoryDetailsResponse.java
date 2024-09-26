package com.tth.order.dto.response.inventory;

import com.tth.order.dto.response.product.ProductListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDetailsResponse {

    private Float quantity;

    private ProductListResponse product;

}
