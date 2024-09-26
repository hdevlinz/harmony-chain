package com.tth.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "warehouse")
public class Warehouse extends BaseEntity implements Serializable {

    @NotNull(message = "{warehouse.name.notNull}")
    @NotBlank(message = "{warehouse.name.notNull}")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "{warehouse.location.notNull}")
    @NotBlank(message = "{warehouse.location.notNull}")
    @Column(nullable = false)
    private String location;

    @Builder.Default
    @NotNull(message = "{warehouse.capacity.notNull}")
    @Column(nullable = false, columnDefinition = "float default 0.0")
    private Float capacity = 0.0f;

    @Builder.Default
    @NotNull(message = "{warehouse.cost.notNull}")
    @Column(nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal cost = BigDecimal.ZERO;

}
