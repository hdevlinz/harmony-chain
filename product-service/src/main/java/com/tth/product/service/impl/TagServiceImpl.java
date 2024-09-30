package com.tth.product.service.impl;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.product.tag.TagResponse;
import com.tth.product.mapper.TagMapper;
import com.tth.product.repository.TagRepository;
import com.tth.product.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<TagResponse> result = this.tagRepository.filter(params, pageable).map(this.tagMapper::toTagResponse);

        return PageResponse.of(result);
    }

}
