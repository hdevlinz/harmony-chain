package com.tth.product.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tth.product.dto.response.category.CategoryResponse;
import com.tth.product.dto.response.supplier.SupplierResponse;
import com.tth.product.dto.response.tag.TagResponse;
import com.tth.product.dto.response.unit.UnitResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsResponse {

    private String id;

    private String name;

    private String image;

    private BigDecimal price;

    private String description;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date expiryDate;

    private SupplierResponse supplier;

    private UnitResponse unit;

    private CategoryResponse category;

    private Set<TagResponse> tags;

}
