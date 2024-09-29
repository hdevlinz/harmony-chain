package com.tth.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tax", indexes = {@Index(name = "idx_tax_region", columnList = "region")})
public class Tax extends BaseEntity implements Serializable {

    @Builder.Default
    @NotNull(message = "{tax.rate.notNull}")
    @DecimalMin(value = "0.01", message = "{tax.rate.min}")
    @DecimalMax(value = "1.00", message = "{tax.rate.max}")
    @Column(nullable = false, precision = 5, scale = 2, columnDefinition = "float default 0.01")
    private BigDecimal rate = BigDecimal.valueOf(0.01);

    @Size(min = 1, max = 15, message = "{tax.region.size}")
    @NotNull(message = "{tax.region.notNull}")
    @NotBlank(message = "{tax.region.notNull}")
    @Column(nullable = false, unique = true, length = 15)
    private String region;

    @JsonIgnore
    @OneToMany(mappedBy = "tax")
    private Set<Invoice> invoices;

}
