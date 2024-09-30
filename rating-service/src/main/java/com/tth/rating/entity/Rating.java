package com.tth.rating.entity;

import com.tth.commonlibrary.enums.CriteriaType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rating")
public class Rating extends BaseEntity implements Serializable {

    @Field(name = "user_id")
    private String userId;

    @Field(name = "supplier_id")
    private String supplierId;

    @Builder.Default
    @NotNull(message = "{rating.notNull}")
    @DecimalMin(value = "1.00", message = "{rating.min}")
    @DecimalMax(value = "5.00", message = "{rating.max}")
    @Field(name = "rating")
    private BigDecimal rating = BigDecimal.valueOf(5);

    private String content;

    @Builder.Default
    @Field(name = "criteria")
    @NotNull(message = "{rating.criteria.notNull}")
    private CriteriaType criteria = CriteriaType.QUALITY;

}
