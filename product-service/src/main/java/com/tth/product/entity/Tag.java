package com.tth.product.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tag")
public class Tag extends BaseEntity implements Serializable {

    @NotNull(message = "{tag.name.notNull}")
    @NotBlank(message = "{tag.name.notNull}")
    @Field(name = "name")
    private String name;

    private String description;

    //    @ManyToMany(mappedBy = "tags")
    private Set<Product> products;

//    @PreRemove
//    private void removeTagFromProduct() {
//        this.products.forEach(product -> product.getTags().remove(this));
//    }

}
