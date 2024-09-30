package com.tth.cart.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cart")
public class Cart extends BaseEntity implements Serializable {

    @Field(name = "user_id")
    private String userId;

    private Set<CartItem> cartItems;

    public void addItem(CartItem item) {
        if (this.cartItems == null) {
            this.cartItems = new HashSet<>();
        }

        this.cartItems.add(item);
    }

    public void removeItem(CartItem item) {
        if (this.cartItems != null) {
            this.cartItems.remove(item);
        }
    }

    public void clearItems() {
        if (this.cartItems != null) {
            this.cartItems.clear();
        }
    }

}
