package com.tth.identity.service;

import com.tth.commonlibrary.dto.PageResponse;
import com.tth.commonlibrary.dto.request.identity.RegisterRequest;
import com.tth.commonlibrary.dto.request.identity.UpdateRequest;
import com.tth.commonlibrary.dto.response.identity.UserResponse;
import com.tth.identity.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User findById(String id);

    boolean existsByUsername(String username);

    UserResponse registration(RegisterRequest request);

    UserResponse getInfo();

    UserResponse updateInfo(UpdateRequest request);

    List<User> findAll();

    PageResponse<UserResponse> findAllWithFilter(Map<String, String> params, int page, int size);

}
