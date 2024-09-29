package com.tth.product.repository.specification;

import com.tth.product.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CategorySpecification {

    Page<Category> filter(Map<String, String> params, Pageable pageable);

}
