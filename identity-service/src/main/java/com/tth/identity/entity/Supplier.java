package com.tth.identity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supplier")
public class Supplier extends BaseEntity implements Serializable {

    @NotNull(message = "{supplier.name.notNull}")
    @NotBlank(message = "{supplier.name.notNull}")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "{supplier.address.notNull}")
    @NotBlank(message = "{supplier.address.notNull}")
    @Column(nullable = false)
    private String address;

    @NotNull(message = "{user.phone.notNull}")
    @NotBlank(message = "{user.phone.notNull}")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    @Column(nullable = false, length = 15)
    private String phone;

    @NotNull(message = "{supplier.contactInfo.notNull}")
    @NotBlank(message = "{supplier.contactInfo.notNull}")
    @Column(name = "contact_info", nullable = false)
    private String contactInfo;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

}
