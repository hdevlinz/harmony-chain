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
@Document(collection = "category")
public class Category extends BaseEntity implements Serializable {

    @NotNull(message = "{category.name.notNull}")
    @NotBlank(message = "{category.name.notNull}")
    @Field(name = "name")
    private String name;

    private String description;

    private Set<Product> products;

}