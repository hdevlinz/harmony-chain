package com.tth.commonlibrary.dto.request.profile.supplier;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequestUpdate {

    private String supplierName;

    private String supplierAddress;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "{user.phone.pattern}")
    private String supplierPhone;

    private String supplierContactInfo;

}
