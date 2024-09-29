package com.tth.product.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.category.CategoryResponse;

import java.util.Map;

public interface CategoryService {

    PageResponse<CategoryResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
