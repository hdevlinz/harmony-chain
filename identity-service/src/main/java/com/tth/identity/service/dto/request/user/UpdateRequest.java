package com.tth.identity.service.dto.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequest {

    // User
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "{user.email.pattern}")
    private String email;
    @Size(min = 6, max = 50, message = "{user.username.size}")
    private String username;
    private String oldPassword;
    @Size(min = 8, max = 300, message = "{user.password.size}")
    private String newPassword;
    private MultipartFile avatar;

    // Customer
    private String customerFirstName;
    private String customerMiddleName;
    private String customerLastName;
    private String customerAddress;
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String customerPhone;
    private Boolean customerGender;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate customerDateOfBirth;

    // Shipper
    private String shipperName;
    private String shipperContactInfo;

    // Supplier
    private String supplierName;
    private String supplierAddress;
    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String supplierPhone;
    private String supplierContactInfo;
}
