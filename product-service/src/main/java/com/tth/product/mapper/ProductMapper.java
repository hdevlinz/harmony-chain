package com.tth.product.mapper;

import com.tth.product.dto.response.ProductDetailsResponse;
import com.tth.product.dto.response.ProductListResponse;
import com.tth.product.entity.Product;
import com.tth.product.mapper.helper.ProductMappingHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMappingHelper.class})
public interface ProductMapper {

    @Mapping(target = "supplier", source = "supplierId", qualifiedByName = "getSupplier")
    ProductDetailsResponse toProductDetailsResponse(Product product);

    @Mapping(target = "supplier", source = "supplierId", qualifiedByName = "getSupplier")
    ProductListResponse toProductListResponse(Product product);

}
