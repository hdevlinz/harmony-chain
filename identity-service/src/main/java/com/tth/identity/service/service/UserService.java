package com.tth.identity.service.service;

import com.tth.identity.service.dto.request.user.RegisterRequest;
import com.tth.identity.service.dto.request.user.UpdateRequest;
import com.tth.identity.service.dto.response.PageResponse;
import com.tth.identity.service.dto.response.user.UserResponse;

import java.util.Map;

public interface UserService {

    boolean existsByUsername(String username);

    UserResponse register(RegisterRequest registerRequest);

    UserResponse getInfo();

    UserResponse updateInfo(UpdateRequest updateRequest);

    PageResponse<UserResponse> findAllWithFilter(Map<String, String> params, int page, int size);
}
