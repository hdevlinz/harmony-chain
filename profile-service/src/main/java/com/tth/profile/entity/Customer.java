package com.tth.profile.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("customer")
public class Customer extends BaseEntity implements Serializable {

    private String userId;

    @NotNull(message = "{customer.firstName.notNull}")
    @NotBlank(message = "{customer.firstName.notNull}")
    private String firstName;

    @NotNull(message = "{customer.middleName.notNull}")
    @NotBlank(message = "{customer.middleName.notNull}")
    private String middleName;

    @NotNull(message = "{customer.lastName.notNull}")
    @NotBlank(message = "{customer.lastName.notNull}")
    private String lastName;

    @NotNull(message = "{customer.address.notNull}")
    @NotBlank(message = "{customer.address.notNull}")
    private String address;

    @NotNull(message = "{user.phone.notNull}")
    @NotBlank(message = "{user.phone.notNull}")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String phone;

    @Builder.Default
    private Boolean gender = true; // true (1): Nữ - false (0): Nam

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    public String getFullName() {
        return String.format("%s %s %s", this.firstName, this.middleName, this.lastName);
    }

}
