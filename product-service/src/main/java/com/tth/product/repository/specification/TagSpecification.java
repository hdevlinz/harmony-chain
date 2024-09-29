package com.tth.product.repository.specification;

import com.tth.product.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface TagSpecification {

    Page<Tag> filter(Map<String, String> params, Pageable pageable);

}
