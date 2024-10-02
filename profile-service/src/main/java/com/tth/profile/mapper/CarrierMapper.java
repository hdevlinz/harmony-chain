package com.tth.profile.mapper;

import com.tth.commonlibrary.dto.request.profile.carrier.CarrierRequestCreate;
import com.tth.commonlibrary.dto.request.profile.carrier.CarrierRequestUpdate;
import com.tth.commonlibrary.dto.response.profile.carrier.CarrierResponse;
import com.tth.profile.entity.Carrier;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CarrierMapper {

    @Named("toCarrier")
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "name", source = "carrierName")
    @Mapping(target = "contactInfo", source = "carrierContactInfo")
    Carrier toCarrier(CarrierRequestCreate request);

    @Named("toCarrierResponse")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "contactInfo", ignore = true)
    CarrierResponse toCarrierResponse(Carrier carrier);

    @Named("updateCarrier")
    @Mapping(target = "name", source = "carrierName")
    @Mapping(target = "contactInfo", source = "carrierContactInfo")
    void updateCarrier(@MappingTarget Carrier carrier, CarrierRequestUpdate request);

}
