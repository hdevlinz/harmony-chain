package com.tth.api.gateway.service.impl;

import com.tth.api.gateway.dto.APIResponse;
import com.tth.api.gateway.dto.request.IntrospectRequest;
import com.tth.api.gateway.dto.response.IntrospectResponse;
import com.tth.api.gateway.repository.IdentityClient;
import com.tth.api.gateway.service.IdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IdentityServiceImpl implements IdentityService {

    private final IdentityClient identityClient;

    @Override
    public Mono<APIResponse<IntrospectResponse>> introspect(String token) {
        return identityClient.introspect(IntrospectRequest.builder().token(token).build());
    }

}
