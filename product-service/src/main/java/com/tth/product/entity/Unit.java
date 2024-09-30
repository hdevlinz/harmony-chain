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
@Document(collection = "unit")
public class Unit extends BaseEntity implements Serializable {

    @NotNull(message = "{unit.name.notNull}")
    @NotBlank(message = "{unit.name.notNull}")
    @Field(name = "name")
    private String name;

    @NotNull(message = "{unit.abbreviation.notNull}")
    @NotBlank(message = "{unit.abbreviation.notNull}")
    @Field(name = "abbreviation")
    private String abbreviation;

    private Set<Product> products;

}
