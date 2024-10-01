package com.tth.product.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.product.category.CategoryResponse;
import com.tth.product.mapper.CategoryMapper;
import com.tth.product.repository.CategoryRepository;
import com.tth.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public PageResponse<CategoryResponse> findAll(Map<String, String> params, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CategoryResponse> result = this.categoryRepository.filter(params, pageable).map(this.categoryMapper::toCategoryResponse);

        return PageResponse.of(result);
    }

}
