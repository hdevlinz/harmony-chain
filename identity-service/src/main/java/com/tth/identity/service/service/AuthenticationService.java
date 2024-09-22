package com.tth.identity.service.service;

import com.nimbusds.jose.JOSEException;
import com.tth.identity.service.dto.request.user.IntrospectRequest;
import com.tth.identity.service.dto.request.user.LogoutRequest;
import com.tth.identity.service.dto.request.user.RefreshTokenRequest;
import com.tth.identity.service.dto.response.user.AuthenticationResponse;
import com.tth.identity.service.dto.response.user.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(String username, String password);

    AuthenticationResponse authenticateOutbound(String code);

    AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
