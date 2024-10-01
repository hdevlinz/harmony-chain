package com.tth.inventory.mapper;

import com.tth.commonlibrary.dto.request.warehouse.WarehouseRequestCreate;
import com.tth.commonlibrary.dto.request.warehouse.WarehouseRequestUpdate;
import com.tth.commonlibrary.dto.response.warehouse.WarehouseResponse;
import com.tth.inventory.entity.Warehouse;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WarehouseMapper {

    @Named("toWarehouse")
    Warehouse toWarehouse(WarehouseRequestCreate request);

    @Named("updateWarehouse")
    Warehouse updateWarehouse(@MappingTarget Warehouse warehouse, WarehouseRequestUpdate request);

    @Named("toWarehouseResponse")
    WarehouseResponse toWarehouseResponse(Warehouse warehouse);

}
