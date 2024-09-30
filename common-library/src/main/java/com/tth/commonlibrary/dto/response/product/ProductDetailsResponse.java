package com.tth.commonlibrary.dto.response.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tth.commonlibrary.dto.response.product.category.CategoryResponse;
import com.tth.commonlibrary.dto.response.product.tag.TagResponse;
import com.tth.commonlibrary.dto.response.product.unit.UnitResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
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

    private CategoryResponse category;

    private Set<UnitResponse> units;

    private Set<TagResponse> tags;

}
