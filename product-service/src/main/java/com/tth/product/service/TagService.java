package com.tth.product.service;

import com.tth.product.dto.PageResponse;
import com.tth.product.dto.response.tag.TagResponse;

import java.util.Map;

public interface TagService {

    PageResponse<TagResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
