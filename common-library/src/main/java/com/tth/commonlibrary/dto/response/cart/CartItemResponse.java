package com.tth.commonlibrary.dto.response.cart;

import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    private Float quantity;

    private BigDecimal unitPrice;

    private ProductListResponse product;

}
