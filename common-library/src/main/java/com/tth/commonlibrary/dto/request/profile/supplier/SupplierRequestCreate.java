package com.tth.commonlibrary.dto.request.profile.supplier;

import com.tth.commonlibrary.dto.request.profile.UserProfileRequestCreate;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SupplierRequestCreate extends UserProfileRequestCreate {

    private String userId;

    private String supplierName;

    private String supplierAddress;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String supplierPhone;

    private String supplierContactInfo;

}
