package com.tth.product.service;

import com.tth.product.dto.PageResponse;
import com.tth.product.dto.response.ProductDetailsResponse;
import com.tth.product.dto.response.ProductListResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {

    ProductDetailsResponse findById(String id);

    List<ProductListResponse> findAllInBatch(Set<String> productIds);

    PageResponse<ProductListResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
