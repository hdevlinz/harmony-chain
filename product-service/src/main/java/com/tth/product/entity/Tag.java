package com.tth.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity implements Serializable {

    @NotNull(message = "{tag.name.notNull}")
    @NotBlank(message = "{tag.name.notNull}")
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "tags")
    private Set<Product> products;

    @PreRemove
    private void removeTagFromProduct() {
        this.products.forEach(product -> product.getTags().remove(this));
    }

}
