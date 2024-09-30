package com.tth.profile.repository.specification;

import com.tth.profile.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CustomerSpecification {

    Page<Customer> filter(Map<String, String> params, Pageable pageable);

}
