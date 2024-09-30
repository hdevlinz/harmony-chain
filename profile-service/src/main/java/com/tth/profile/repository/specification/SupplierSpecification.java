package com.tth.profile.repository.specification;

import com.tth.profile.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface SupplierSpecification {

    Page<Supplier> filter(Map<String, String> params, Pageable pageable);

}
