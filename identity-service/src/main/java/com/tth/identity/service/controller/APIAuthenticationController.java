package com.tth.identity.service.controller;

import com.nimbusds.jose.JOSEException;
import com.tth.identity.service.dto.request.user.IntrospectRequest;
import com.tth.identity.service.dto.request.user.LoginRequest;
import com.tth.identity.service.dto.request.user.LogoutRequest;
import com.tth.identity.service.dto.request.user.RefreshTokenRequest;
import com.tth.identity.service.dto.response.APIResponse;
import com.tth.identity.service.dto.response.user.AuthenticationResponse;
import com.tth.identity.service.dto.response.user.IntrospectResponse;
import com.tth.identity.service.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth", produces = "application/json; charset=UTF-8")
public class APIAuthenticationController {

    private final AuthenticationService authenticateUser;

    @PostMapping(path = "/token")
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginRequest request, BindingResult bindingResult) {
        AuthenticationResponse result = this.authenticateUser.authenticate(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(APIResponse.<AuthenticationResponse>builder().result(result).build());
    }

    @PostMapping("/outbound/authenticate")
    public ResponseEntity<?> authenticateOutbound(@RequestParam("code") String code) {
        AuthenticationResponse result = this.authenticateUser.authenticateOutbound(code);

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
