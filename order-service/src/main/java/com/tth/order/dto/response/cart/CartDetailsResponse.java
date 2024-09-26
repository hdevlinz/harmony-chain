package com.tth.order.dto.response.cart;

import com.tth.order.dto.response.product.ProductListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailsResponse {

    private Float quantity;

    private BigDecimal unitPrice;

    private ProductListResponse product;

}
