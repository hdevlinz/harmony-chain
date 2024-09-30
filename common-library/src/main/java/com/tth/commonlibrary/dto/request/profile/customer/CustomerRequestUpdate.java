package com.tth.commonlibrary.dto.request.profile.customer;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestUpdate {

    private String customerFirstName;

    private String customerMiddleName;

    private String customerLastName;

    private String customerAddress;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String customerPhone;

    private Boolean customerGender;

    private LocalDate customerDateOfBirth;

}
