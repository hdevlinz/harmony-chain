package com.tth.commonlibrary.dto.response.profile.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tth.commonlibrary.dto.response.profile.UserProfileResponse;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerResponse extends UserProfileResponse {

    private String id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String address;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String phone;

    private Boolean gender;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    @JsonProperty("fullName")
    private String getFullName() {
        return String.format("%s %s %s", this.firstName, this.middleName, this.lastName);
    }

}
