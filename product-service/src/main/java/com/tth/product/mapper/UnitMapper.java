package com.tth.product.mapper;

import com.tth.commonlibrary.dto.response.product.unit.UnitResponse;
import com.tth.product.entity.Unit;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UnitMapper {

    @Named("toUnitResponse")
    UnitResponse toUnitResponse(Unit unit);

}
