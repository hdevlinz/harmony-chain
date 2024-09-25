package com.tth.product.service;

import com.tth.product.dto.PageResponse;
import com.tth.product.dto.response.ProductListResponse;
import com.tth.product.entity.Product;

import java.util.Map;

public interface ProductService {

    Product findById(String id);

    PageResponse<ProductListResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
