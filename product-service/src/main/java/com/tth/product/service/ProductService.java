package com.tth.product.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.product.ProductDetailsResponse;
import com.tth.commonlibrary.dto.response.product.ProductListResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {

    List<ProductListResponse> findAllInBatch(Set<String> productIds);

    ProductDetailsResponse findById(String id);

    PageResponse<ProductListResponse> findAll(Map<String, String> params, int page, int size);

}
