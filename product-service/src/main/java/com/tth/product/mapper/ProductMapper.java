package com.tth.product.mapper;

import com.tth.commonlibrary.dto.response.product.ProductDetailsResponse;
import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import com.tth.product.entity.Product;
import com.tth.product.mapper.helper.ProductMappingHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMappingHelper.class})
public interface ProductMapper {

    @Mapping(target = "supplier", source = "supplierId", qualifiedByName = "getSupplier")
    ProductListResponse toProductListResponse(Product product);

    @Mapping(target = "supplier", source = "supplierId", qualifiedByName = "getSupplier")
    ProductDetailsResponse toProductDetailsResponse(Product product);

}
