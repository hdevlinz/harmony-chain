package com.tth.profile.repository.specification;

import com.tth.profile.entity.Carrier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface CarrierSpecification {

    Page<Carrier> filter(Map<String, String> params, Pageable pageable);

}
