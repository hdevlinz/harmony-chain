package com.tth.gateway.service;

import com.tth.gateway.dto.APIResponse;
import com.tth.gateway.dto.response.IntrospectResponse;
import reactor.core.publisher.Mono;

public interface IdentityService {

    Mono<APIResponse<IntrospectResponse>> introspect(String token);

}
