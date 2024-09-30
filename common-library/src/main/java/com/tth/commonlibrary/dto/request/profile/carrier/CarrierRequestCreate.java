package com.tth.commonlibrary.dto.request.profile.carrier;

import com.tth.commonlibrary.dto.request.profile.UserProfileRequestCreate;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CarrierRequestCreate extends UserProfileRequestCreate {

    private String userId;

    private String carrierName;

    private String carrierContactInfo;

}
