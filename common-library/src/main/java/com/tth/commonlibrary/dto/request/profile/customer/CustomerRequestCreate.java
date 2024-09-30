package com.tth.commonlibrary.dto.request.profile.customer;

import com.tth.commonlibrary.dto.request.profile.UserProfileRequestCreate;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerRequestCreate extends UserProfileRequestCreate {

    private String userId;

    private String customerFirstName;

    private String customerMiddleName;

    private String customerLastName;

    private String customerAddress;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String customerPhone;

}
