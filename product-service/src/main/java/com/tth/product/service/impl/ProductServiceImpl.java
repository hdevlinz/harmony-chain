package com.tth.product.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.product.ProductDetailsResponse;
import com.tth.commonlibrary.dto.response.product.ProductListResponse;
import com.tth.commonlibrary.enums.ErrorCode;
import com.tth.commonlibrary.exception.AppException;
import com.tth.product.entity.Product;
import com.tth.product.mapper.ProductMapper;
import com.tth.product.repository.ProductRepository;
import com.tth.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductListResponse> findAllInBatch(Set<String> productIds) {
        return this.productRepository.findAllById(productIds).stream().map(this.productMapper::toProductListResponse).toList();
    }

    @Override
    public ProductDetailsResponse findById(String id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        return this.productMapper.toProductDetailsResponse(product);
    }

    @Override
    public PageResponse<ProductListResponse> findAll(Map<String, String> params, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductListResponse> result = this.productRepository.filter(params, pageable).map(this.productMapper::toProductListResponse);

        return PageResponse.of(result);
    }

}
