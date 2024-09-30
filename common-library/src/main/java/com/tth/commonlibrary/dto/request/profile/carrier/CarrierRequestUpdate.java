package com.tth.commonlibrary.dto.request.profile.carrier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarrierRequestUpdate {

    private String carrierName;

    private String carrierContactInfo;

}
