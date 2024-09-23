package com.tth.identity.service;

import com.nimbusds.jose.JOSEException;
import com.tth.identity.dto.request.IntrospectRequest;
import com.tth.identity.dto.request.LogoutRequest;
import com.tth.identity.dto.request.RefreshTokenRequest;
import com.tth.identity.dto.response.AuthenticationResponse;
import com.tth.identity.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(String username, String password);

    AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

}
