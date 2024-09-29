package com.tth.inventory.mapper;

import com.tth.order.dto.request.warehouse.CreateWarehouseRequest;
import com.tth.order.dto.request.warehouse.UpdateWarehouseRequest;
import com.tth.order.dto.response.warehouse.WarehouseResponse;
import com.tth.order.entity.Warehouse;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WarehouseMapper {

    @Named("toWarehouse")
    Warehouse toWarehouse(CreateWarehouseRequest request);

    @Named("updateWarehouse")
    Warehouse updateWarehouse(@MappingTarget Warehouse warehouse, UpdateWarehouseRequest request);

    @Named("toWarehouseResponse")
    WarehouseResponse toWarehouseResponse(Warehouse warehouse);

}
