package com.tth.product.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.response.product.tag.TagResponse;

import java.util.Map;

public interface TagService {

    PageResponse<TagResponse> findAll(Map<String, String> params, int page, int size);

}
