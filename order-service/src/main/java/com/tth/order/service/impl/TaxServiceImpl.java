package com.tth.order.service.impl;

import com.tth.order.dto.PageResponse;
import com.tth.order.entity.Tax;
import com.tth.order.repository.TaxRepository;
import com.tth.order.service.TaxService;
import com.tth.order.service.specification.TaxSpecification;
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
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;

    @Override
    public PageResponse<Tax> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Tax> specification = TaxSpecification.filter(params);
        Pageable pageable = PageRequest.of(page, size);
        Page<Tax> taxes = taxRepository.findAll(specification, pageable);

        return PageResponse.of(taxes);
    }

}
