package com.tth.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product", indexes = {
        @Index(name = "product_supplier_id_index", columnList = "supplier_id")
})
public class Product extends BaseEntity implements Serializable {

    @Transient
    MultipartFile file;

    @Column(name = "supplier_id", nullable = false)
    private String supplierId;

    @NotNull(message = "{product.name.notNull}")
    @NotBlank(message = "{product.name.notNull}")
    @Column(nullable = false)
    private String name;

    @Column(length = 300)
    private String image;

    @Builder.Default
    @NotNull(message = "{product.price.notNull}")
    @Column(nullable = false, precision = 11, scale = 2, columnDefinition = "decimal default 0.0")
    private BigDecimal price = BigDecimal.ZERO;

    private String description;

    @NotNull(message = "{product.expiryDate.notNull}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToMany
    @JoinTable(name = "product_tag",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @PreRemove
    public void preRemove() {
        this.tags.clear();
    }

}
