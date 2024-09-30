package com.tth.rating.repository.httpclient;

import com.tth.commonlibrary.dto.APIResponse;
import com.tth.commonlibrary.dto.response.identity.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "identity-service", url = "${app.services.identity.url}")
public interface IdentityClient {

    @GetMapping("/internal/users")
    APIResponse<List<UserResponse>> listUsers();

    @GetMapping("/internal/users/{userId}")
    APIResponse<UserResponse> getUser(@PathVariable String userId);

}
