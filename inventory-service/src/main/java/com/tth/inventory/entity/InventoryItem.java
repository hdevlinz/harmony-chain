package com.tth.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_details", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"inventory_id", "product_id"})
})
public class InventoryItem extends BaseEntity implements Serializable {

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Builder.Default
    @NotNull(message = "{inventoryDetails.quantity.notNull}")
    @Column(nullable = false, columnDefinition = "float default 0")
    private Float quantity = 0.0f;

    @ManyToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id", nullable = false)
    private Inventory inventory;

}
