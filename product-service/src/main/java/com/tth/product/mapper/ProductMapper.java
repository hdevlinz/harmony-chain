package com.tth.product.mapper;

import com.tth.product.dto.response.ProductDetailsResponse;
import com.tth.product.dto.response.ProductListResponse;
import com.tth.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDetailsResponse toProductDetailsResponse(Product product);

    ProductListResponse toProductListResponse(Product product);

}
