package com.tth.product.mapper;

import com.tth.commonlibrary.dto.response.category.CategoryResponse;
import com.tth.product.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toCategoryResponse(Category category);

}
