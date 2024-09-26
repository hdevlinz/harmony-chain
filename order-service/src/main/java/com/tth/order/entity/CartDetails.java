package com.tth.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "cart_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cart_id", "product_id"})
})
public class CartDetails extends BaseEntity implements Serializable {

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Builder.Default
    @NotNull(message = "Quantity is required")
    @Column(nullable = false, columnDefinition = "float default 0")
    private Float quantity = 0.0f;

    @Builder.Default
    @NotNull(message = "Unit price is required")
    @Column(name = "unit_price", nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

}
