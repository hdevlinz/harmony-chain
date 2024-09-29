package com.tth.commonlibrary.dto.request.rating;

import com.tth.commonlibrary.enums.CriteriaType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequestUpdate {

    @DecimalMin(value = "1.00", message = "{rating.min}")
    @DecimalMax(value = "5.00", message = "{rating.max}")
    private BigDecimal rating;

    private String content;

    private CriteriaType criteria;

}
