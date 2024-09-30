package com.tth.commonlibrary.dto.request.identity;

import com.tth.commonlibrary.enums.UserRole;
import com.tth.commonlibrary.validator.constraint.RoleBasedConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RoleBasedConstraint
public class RegisterRequest {

    // User
    @NotNull(message = "{user.email.notNull}")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "{user.email.pattern}")
    private String email;
    @NotNull(message = "{user.username.notNull}")
    @Size(min = 6, max = 50, message = "{user.username.size}")
    private String username;
    @NotNull(message = "{user.password.notNull}")
    @Size(min = 8, max = 300, message = "{user.password.size}")
    private String password;
    @NotNull(message = "{user.role.notNull}")
    private UserRole role;

    // Customer
    private String customerFirstName;
    private String customerMiddleName;
    private String customerLastName;
    private String customerAddress;
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String customerPhone;

    // Shipper
    private String carrierName;
    private String carrierContactInfo;

    // Supplier
    private String supplierName;
    private String supplierAddress;
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String supplierPhone;
    private String supplierContactInfo;

}
