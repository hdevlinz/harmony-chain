package com.tth.identity.controller;

import com.nimbusds.jose.JOSEException;
import com.tth.identity.dto.APIResponse;
import com.tth.identity.dto.request.IntrospectRequest;
import com.tth.identity.dto.request.LoginRequest;
import com.tth.identity.dto.request.LogoutRequest;
import com.tth.identity.dto.request.RefreshTokenRequest;
import com.tth.identity.dto.response.AuthenticationResponse;
import com.tth.identity.dto.response.IntrospectResponse;
import com.tth.identity.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth", produces = "application/json; charset=UTF-8")
public class APIAuthenticationController {

    private final AuthenticationService authenticateUser;

    @PostMapping(path = "/token")
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginRequest request) {
        AuthenticationResponse result = this.authenticateUser.authenticate(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(APIResponse.<AuthenticationResponse>builder().result(result).build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) throws ParseException, JOSEException {
        AuthenticationResponse result = this.authenticateUser.refreshToken(request);

        return ResponseEntity.ok(APIResponse.<AuthenticationResponse>builder().result(result).build());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        this.authenticateUser.logout(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/introspect")
    public ResponseEntity<?> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse result = this.authenticateUser.introspect(request);

        return ResponseEntity.ok(APIResponse.<IntrospectResponse>builder().result(result).build());
    }

}
