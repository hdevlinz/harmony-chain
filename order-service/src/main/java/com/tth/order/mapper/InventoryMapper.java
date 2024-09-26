package com.tth.order.mapper;

import com.tth.order.dto.request.inventory.CreateInventoryRequest;
import com.tth.order.dto.request.inventory.UpdateInventoryRequest;
import com.tth.order.dto.response.inventory.InventoryDetailsResponse;
import com.tth.order.dto.response.inventory.InventoryResponse;
import com.tth.order.entity.Inventory;
import com.tth.order.entity.InventoryDetails;
import com.tth.order.mapper.helper.InventoryHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {
                InventoryHelper.class,
                WarehouseMapper.class
        },
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface InventoryMapper {

    @Named("toInventory")
    @Mapping(target = "warehouse", source = "warehouseId", qualifiedByName = "findWarehouseById")
    Inventory toInventory(CreateInventoryRequest request);

    @Named("updateInventory")
    @Mapping(target = "warehouse", source = "warehouseId", qualifiedByName = "findWarehouseById")
    Inventory updateInventory(@MappingTarget Inventory inventory, UpdateInventoryRequest request);

    @Named("toInventoryResponse")
    @Mapping(target = "warehouse", source = "warehouse", qualifiedByName = "toWarehouseResponse")
    @Mapping(target = "inventoryDetails", source = "inventoryDetails", qualifiedByName = "mapInventoryDetailsSetToResponse")
    InventoryResponse toInventoryResponse(Inventory inventory);

    @Mapping(target = "product", source = "productId", qualifiedByName = "mapInventoryDetailsToResponse")
    InventoryDetailsResponse toInventoryDetailsResponse(InventoryDetails inventoryDetails);

}
