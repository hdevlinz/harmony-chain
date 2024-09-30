package com.tth.profile.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("supplier")
public class Supplier extends BaseEntity implements Serializable {

    @Property(name = "user_id")
    private String userId;

    @NotNull(message = "{supplier.name.notNull}")
    @NotBlank(message = "{supplier.name.notNull}")
    private String name;

    @NotNull(message = "{supplier.address.notNull}")
    @NotBlank(message = "{supplier.address.notNull}")
    private String address;

    @NotNull(message = "{user.phone.notNull}")
    @NotBlank(message = "{user.phone.notNull}")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String phone;

    @NotNull(message = "{supplier.contactInfo.notNull}")
    @NotBlank(message = "{supplier.contactInfo.notNull}")
    private String contactInfo;

}
