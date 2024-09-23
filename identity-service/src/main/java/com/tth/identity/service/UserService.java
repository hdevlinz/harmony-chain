package com.tth.identity.service;

import com.tth.identity.dto.request.RegisterRequest;
import com.tth.identity.dto.request.UpdateRequest;
import com.tth.identity.dto.response.PageResponse;
import com.tth.identity.dto.response.UserResponse;

import java.util.Map;

public interface UserService {

    boolean existsByUsername(String username);

    UserResponse registration(RegisterRequest registerRequest);

    UserResponse getInfo();

    UserResponse updateInfo(UpdateRequest updateRequest);

    PageResponse<UserResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
