package com.tth.product.mapper;

import com.tth.product.dto.response.unit.UnitResponse;
import com.tth.product.entity.Unit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    UnitResponse toUnitResponse(Unit unit);

}
