package com.tth.identity.services.service;

import com.tth.identity.services.dto.request.RegisterRequest;
import com.tth.identity.services.dto.request.UpdateRequest;
import com.tth.identity.services.dto.response.PageResponse;
import com.tth.identity.services.dto.response.UserResponse;

import java.util.Map;

public interface UserService {

    boolean existsByUsername(String username);

    UserResponse registration(RegisterRequest registerRequest);

    UserResponse getInfo();

    UserResponse updateInfo(UpdateRequest updateRequest);

    PageResponse<UserResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
