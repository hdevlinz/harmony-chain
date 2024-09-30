package com.tth.commonlibrary.dto.response.product;

import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
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

    private String id;

    private String name;

    private String image;

    private BigDecimal price;

    private String description;

    private SupplierResponse supplier;

}
