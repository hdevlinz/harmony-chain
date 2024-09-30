package com.tth.commonlibrary.dto.response.rating;

import com.tth.commonlibrary.dto.response.identity.UserResponse;
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
public class RatingResponse {

    private Long id;

    private BigDecimal rating;

    private String content;

    private String criteria;

    private SupplierResponse supplier;

    private UserResponse user;

}
