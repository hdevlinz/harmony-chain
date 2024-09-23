package com.tth.api.gateway.service;

import com.tth.api.gateway.dto.response.APIResponse;
import com.tth.api.gateway.dto.response.IntrospectResponse;
import reactor.core.publisher.Mono;

public interface IdentityService {

    Mono<APIResponse<IntrospectResponse>> introspect(String token);

}
