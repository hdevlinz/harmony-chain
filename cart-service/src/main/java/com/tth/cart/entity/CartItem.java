package com.tth.cart.entity;

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
@Document(collection = "cart_items")
public class CartItem extends BaseEntity implements Serializable {

    @Field(name = "product_id")
    private String productId;

    @Field(name = "cart_id")
    private String cartId;

    @Builder.Default
    @NotNull(message = "Quantity is required")
    @Field(name = "quantity")
    private Float quantity = 0.0f;

    @Builder.Default
    @NotNull(message = "Unit price is required")
    @Field(name = "unit_price")
    private BigDecimal unitPrice = BigDecimal.ZERO;

}
