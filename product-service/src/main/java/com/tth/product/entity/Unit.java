package com.tth.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "unit")
public class Unit extends BaseEntity implements Serializable {

    @NotNull(message = "{unit.name.notNull}")
    @NotBlank(message = "{unit.name.notNull}")
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull(message = "{unit.abbreviation.notNull}")
    @NotBlank(message = "{unit.abbreviation.notNull}")
    @Column(nullable = false, unique = true)
    private String abbreviation;

    @OneToMany(mappedBy = "unit")
    private Set<Product> products;

}
