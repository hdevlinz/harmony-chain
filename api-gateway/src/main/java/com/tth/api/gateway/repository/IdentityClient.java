package com.tth.api.gateway.repository;

import com.tth.api.gateway.dto.APIResponse;
import com.tth.api.gateway.dto.request.IntrospectRequest;
import com.tth.api.gateway.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {

    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<APIResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);

}
