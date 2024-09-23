package com.tth.identity.services.service;

import com.nimbusds.jose.JOSEException;
import com.tth.identity.services.dto.request.IntrospectRequest;
import com.tth.identity.services.dto.request.LogoutRequest;
import com.tth.identity.services.dto.request.RefreshTokenRequest;
import com.tth.identity.services.dto.response.AuthenticationResponse;
import com.tth.identity.services.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(String username, String password);

    AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

}
