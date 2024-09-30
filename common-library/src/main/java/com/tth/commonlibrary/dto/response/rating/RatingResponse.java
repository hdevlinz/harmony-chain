package com.tth.commonlibrary.dto.response.rating;

import com.tth.commonlibrary.dto.response.identity.UserResponse;
import com.tth.commonlibrary.dto.response.profile.supplier.SupplierResponse;
import com.tth.commonlibrary.enums.CriteriaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {

    private String id;

    private BigDecimal rating;

    private String content;

    private CriteriaType criteria;

    private UserResponse user;

    private SupplierResponse supplier;

}
