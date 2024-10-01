package com.tth.product.mapper;

import com.tth.commonlibrary.dto.response.product.category.CategoryResponse;
import com.tth.product.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CategoryMapper {

    @Named("toCategoryResponse")
    CategoryResponse toCategoryResponse(Category category);

}
