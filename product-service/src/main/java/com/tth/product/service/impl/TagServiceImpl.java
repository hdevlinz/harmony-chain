package com.tth.product.service.impl;

import com.tth.product.dto.PageResponse;
import com.tth.product.dto.response.tag.TagResponse;
import com.tth.product.entity.Tag;
import com.tth.product.mapper.TagMapper;
import com.tth.product.repository.TagRepository;
import com.tth.product.service.TagService;
import com.tth.product.service.specification.TagSpecification;
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
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public PageResponse<TagResponse> findAllWithFilter(Map<String, String> params, int page, int size) {
        Specification<Tag> spec = TagSpecification.filter(params);

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<TagResponse> result = this.tagRepository.findAll(spec, pageable).map(this.tagMapper::toTagResponse);

        return PageResponse.of(result);
    }

}
