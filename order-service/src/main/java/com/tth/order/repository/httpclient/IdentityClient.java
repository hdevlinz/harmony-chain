package com.tth.order.repository.httpclient;

import com.tth.order.dto.APIResponse;
import com.tth.order.dto.response.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "identity-service", url = "${app.services.identity.url}")
public interface IdentityClient {

    @GetMapping(path = "/internal/users")
    APIResponse<List<User>> getAllUser();

}
