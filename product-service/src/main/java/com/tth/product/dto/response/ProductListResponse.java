package com.tth.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponse {

    private Long id;

    private String name;

    private String image;

    private BigDecimal price;

    private String description;

}
