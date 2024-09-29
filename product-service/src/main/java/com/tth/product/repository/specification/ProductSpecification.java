package com.tth.product.repository.specification;

import com.tth.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ProductSpecification {

    Page<Product> filter(Map<String, String> params, Pageable pageable);

}
