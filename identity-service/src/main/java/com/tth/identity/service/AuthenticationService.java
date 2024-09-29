package com.tth.identity.service;

import com.nimbusds.jose.JOSEException;
import com.tth.commonlibrary.dto.request.identity.IntrospectRequest;
import com.tth.commonlibrary.dto.request.identity.LogoutRequest;
import com.tth.commonlibrary.dto.request.identity.RefreshTokenRequest;
import com.tth.commonlibrary.dto.response.identity.AuthenticationResponse;
import com.tth.commonlibrary.dto.response.identity.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(String username, String password);

    AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

}
