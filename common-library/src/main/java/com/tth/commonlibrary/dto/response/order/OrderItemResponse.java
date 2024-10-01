package com.tth.commonlibrary.dto.response.order;

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
public class OrderItemResponse {

    private String id;

    private Float quantity;

    private BigDecimal unitPrice;

    private ProductListResponse product;

}
