package com.tth.inventory.mapper;

import com.tth.commonlibrary.dto.request.inventory.InventoryItemRequestCreate;
import com.tth.commonlibrary.dto.request.inventory.InventoryItemRequestUpdate;
import com.tth.commonlibrary.dto.response.inventory.InventoryItemResponse;
import com.tth.inventory.entity.InventoryItem;
import com.tth.inventory.mapper.helper.MappingHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {MappingHelper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface InventoryItemMapper {

    @Named("toInventoryItem")
    @Mapping(target = "inventory", source = "inventoryId", qualifiedByName = "getInventoryById")
    InventoryItem toInventoryItem(InventoryItemRequestCreate request);

    @Named("updateInventoryItem")
    InventoryItem updateInventoryItem(@MappingTarget InventoryItem inventoryItem, InventoryItemRequestUpdate request);

    @Named("toInventoryItemResponse")
    @Mapping(target = "product", source = "productId", qualifiedByName = "getProductById")
    InventoryItemResponse toInventoryItemResponse(InventoryItem inventoryItem);

}
