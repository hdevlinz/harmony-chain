package com.tth.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"order_id", "product_id"})
})
public class OrderDetails extends BaseEntity implements Serializable {

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "float default 0")
    private Float quantity = 0.0f;

    @Builder.Default
    @Column(name = "unit_price", nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal unitPrice = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "inventory_details_id", referencedColumnName = "id", nullable = false)
    private InventoryDetails inventoryDetails;

}
