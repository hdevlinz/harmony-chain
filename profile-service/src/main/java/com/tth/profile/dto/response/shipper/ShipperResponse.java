package com.tth.profile.dto.response.shipper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipperResponse {

    private String id;

    private String name;

    private String contactInfo;

}
