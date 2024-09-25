package com.tth.product.service;

import com.tth.product.dto.PageResponse;
import com.tth.product.dto.response.category.CategoryResponse;

import java.util.Map;

public interface CategoryService {

    PageResponse<CategoryResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
