package com.tth.profile.entity;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("carrier")
public class Carrier extends BaseEntity implements Serializable {

    private String userId;

    @NotNull(message = "{carrier.name.notnull}")
    @NotBlank(message = "{carrier.name.notnull}")
    private String name;

    @NotNull(message = "{carrier.contactInfo.notnull}")
    @NotBlank(message = "{carrier.contactInfo.notnull}")
    private String contactInfo;

    @Builder.Default
    @DecimalMin(value = "1.00", message = "{carrier.rating.min")
    @DecimalMax(value = "5.00", message = "{carrier.rating.max")
    private BigDecimal rating = BigDecimal.valueOf(5);

}
