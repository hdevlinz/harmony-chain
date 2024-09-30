package com.tth.commonlibrary.dto.response.profile.carrier;

import com.tth.commonlibrary.dto.response.profile.UserProfileResponse;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CarrierResponse extends UserProfileResponse {

    private String id;

    private String name;

    private String contactInfo;

}
