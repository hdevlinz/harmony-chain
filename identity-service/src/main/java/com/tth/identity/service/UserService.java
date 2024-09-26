package com.tth.identity.service;

import com.tth.identity.dto.PageResponse;
import com.tth.identity.dto.request.RegisterRequest;
import com.tth.identity.dto.request.UpdateRequest;
import com.tth.identity.dto.response.UserResponse;
import com.tth.identity.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    boolean existsByUsername(String username);

    UserResponse registration(RegisterRequest request);

    UserResponse getInfo();

    UserResponse updateInfo(UpdateRequest request);

    List<User> findAll();

    PageResponse<UserResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
