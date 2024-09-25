package com.tth.product.service.impl;

import com.tth.product.dto.PageResponse;
import com.tth.product.dto.response.category.CategoryResponse;
import com.tth.product.entity.Category;
import com.tth.product.mapper.CategoryMapper;
import com.tth.product.repository.CategoryRepository;
import com.tth.product.service.CategoryService;
import com.tth.product.service.specification.CategorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public PageResponse<CategoryResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Category> spec = CategorySpecification.filter(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CategoryResponse> result = this.categoryRepository.findAll(spec, pageable).map(this.categoryMapper::toCategoryResponse);

        return PageResponse.of(result);
    }

}
