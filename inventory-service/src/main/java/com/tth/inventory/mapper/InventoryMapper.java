package com.tth.inventory.mapper;

import com.tth.commonlibrary.dto.request.inventory.InventoryRequestCreate;
import com.tth.commonlibrary.dto.request.inventory.InventoryRequestUpdate;
import com.tth.commonlibrary.dto.response.inventory.InventoryResponse;
import com.tth.inventory.entity.Inventory;
import com.tth.inventory.mapper.helper.InventoryHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {InventoryHelper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface InventoryMapper {

    @Named("toInventory")
    @Mapping(target = "warehouse", source = "warehouseId", qualifiedByName = "getWarehouseById")
    Inventory toInventory(InventoryRequestCreate request);

    @Named("updateInventory")
    @Mapping(target = "warehouse", source = "warehouseId", qualifiedByName = "getWarehouseById")
    Inventory updateInventory(@MappingTarget Inventory inventory, InventoryRequestUpdate request);

    @Named("toInventoryResponse")
    @Mapping(target = "inventoryDetails", source = "inventoryDetails", qualifiedByName = "mapInventoryDetailsSetToResponse")
    InventoryResponse toInventoryResponse(Inventory inventory);

}
