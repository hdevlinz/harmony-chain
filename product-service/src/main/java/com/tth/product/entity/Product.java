package com.tth.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
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
@Document(collection = "product")
public class Product extends BaseEntity implements Serializable {

    @Transient
    MultipartFile file;

    @Field(name = "supplier_id")
    private String supplierId;

    @NotNull(message = "{product.name.notNull}")
    @NotBlank(message = "{product.name.notNull}")
    @Field(name = "name")
    private String name;

    @Field(name = "image")
    private String image;

    @Builder.Default
    @NotNull(message = "{product.price.notNull}")
    @Field(name = "price")
    private BigDecimal price = BigDecimal.ZERO;

    private String description;

    @NotNull(message = "{product.expiryDate.notNull}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Field(name = "expiry_date")
    private LocalDate expiryDate;

    @Field(name = "category_id")
    private Category category;

    private Set<Unit> units;

    private Set<Tag> tags;

}
