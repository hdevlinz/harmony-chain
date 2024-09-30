package com.tth.profile.mapper;

import com.tth.commonlibrary.dto.request.profile.carrier.CarrierRequestCreate;
import com.tth.commonlibrary.dto.request.profile.carrier.CarrierRequestUpdate;
import com.tth.commonlibrary.dto.response.profile.carrier.CarrierResponse;
import com.tth.profile.entity.Carrier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarrierMapper {

    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "name", source = "carrierName")
    @Mapping(target = "contactInfo", source = "carrierContactInfo")
    Carrier toCarrier(CarrierRequestCreate request);

    @Named("mapCarrierToResponse")
    CarrierResponse toCarrierResponse(Carrier carrier);

    List<CarrierResponse> toCarrierResponse(List<Carrier> carriers);

    @Mapping(target = "name", source = "carrierName")
    @Mapping(target = "contactInfo", source = "carrierContactInfo")
    void updateCarrier(@MappingTarget Carrier carrier, CarrierRequestUpdate request);

}
