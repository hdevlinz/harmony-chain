package com.tth.product.repository.specification;

import com.tth.product.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UnitSpecification {

    Page<Unit> filter(Map<String, String> params, Pageable pageable);

}
