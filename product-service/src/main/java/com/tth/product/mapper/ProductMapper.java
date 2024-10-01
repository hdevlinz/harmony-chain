package com.tth.product.mapper;

import com.tth.commonlibrary.dto.response.product.ProductDetailsResponse;
import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import com.tth.product.entity.Product;
import com.tth.product.mapper.helper.MappingHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {MappingHelper.class},
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {

    @Named("toProductListResponse")
    @Mapping(target = "supplier", source = "supplierId", qualifiedByName = "getSupplierById")
    ProductListResponse toProductListResponse(Product product);

    @Named("toProductDetailsResponse")
    @Mapping(target = "supplier", source = "supplierId", qualifiedByName = "getSupplierById")
    ProductDetailsResponse toProductDetailsResponse(Product product);

}
