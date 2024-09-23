package com.tth.identity.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tth.identity.enums.UserRole;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    // User
    private String email;
    private String username;
    private String avatar;
    private UserRole role;
    //    private Boolean confirm;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastLogin;

    // Customer
    private String customerFirstName;
    private String customerMiddleName;
    private String customerLastName;
    private String customerAddress;
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String customerPhone;
    private Boolean customerGender;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate customerDateOfBirth;

    // Shipper
    private String shipperName;
    private String shipperContactInfo;

    // Supplier
    private String supplieName;
    private String supplieAddress;
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String suppliePhone;
    private String supplieContactInfo;

}
