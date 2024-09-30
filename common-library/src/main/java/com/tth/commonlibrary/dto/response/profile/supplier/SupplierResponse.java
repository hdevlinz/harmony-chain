package com.tth.commonlibrary.dto.response.profile.supplier;

import com.tth.commonlibrary.dto.response.profile.UserProfileResponse;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SupplierResponse extends UserProfileResponse {

    private String id;

    private String name;

    private String address;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String phone;

    private String contactInfo;

}
