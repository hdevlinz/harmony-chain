package com.tth.identity.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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
@Table(name = "shipper")
public class Shipper extends BaseEntity implements Serializable {

    @NotNull(message = "{shipper.name.notnull}")
    @NotBlank(message = "{shipper.name.notnull}")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "{shipper.contactInfo.notnull}")
    @NotBlank(message = "{shipper.contactInfo.notnull}")
    @Column(name = "contact_info", nullable = false)
    private String contactInfo;

    @Builder.Default
    @DecimalMin(value = "1.00", message = "{shipper.rating.min")
    @DecimalMax(value = "5.00", message = "{shipper.rating.max")
    @Column(precision = 2, scale = 1, columnDefinition = "float default 5.0")
    private BigDecimal rating = BigDecimal.valueOf(5);

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
