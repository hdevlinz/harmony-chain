package com.tth.identity.service.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tth.identity.service.dto.request.ExchangeTokenRequest;
import com.tth.identity.service.dto.request.user.IntrospectRequest;
import com.tth.identity.service.dto.request.user.LogoutRequest;
import com.tth.identity.service.dto.request.user.RefreshTokenRequest;
import com.tth.identity.service.dto.response.ExchangeTokenResponse;
import com.tth.identity.service.dto.response.user.AuthenticationResponse;
import com.tth.identity.service.dto.response.user.IntrospectResponse;
import com.tth.identity.service.entity.InvalidatedToken;
import com.tth.identity.service.entity.User;
import com.tth.identity.service.enums.ErrorCode;
import com.tth.identity.service.exception.AppException;
import com.tth.identity.service.repository.InvalidatedTokenRepository;
import com.tth.identity.service.repository.UserRepository;
import com.tth.identity.service.repository.httpclient.OutboundIdentityClient;
import com.tth.identity.service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

    private final UserRepository userRepository;
    private final OutboundIdentityClient outboundIdentityClient;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Value("${jwt.signer-key}")
    private String SIGNER_KEY;
    @Value("${jwt.valid-duration}")
    private long VALID_DURATION;
    @Value("${jwt.refreshable-duration}")
    private long REFRESHABLE_DURATION;
    @Value("${outbound.identity.client-id}")
    private String CLIENT_ID;
    @Value("${outbound.identity.client-secret}")
    private String CLIENT_SECRET;
    @Value("${outbound.identity.grant-type}")
    private String GRANT_TYPE;
    @Value("${outbound.identity.redirect-uri}")
    private String REDIRECT_URI;

    @Override
    public AuthenticationResponse authenticate(String username, String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = this.generateToken(user);
        user.setLastLogin(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        this.userRepository.save(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public AuthenticationResponse authenticateOutbound(String code) {
        ExchangeTokenResponse response = this.outboundIdentityClient.exchangeToken(
                ExchangeTokenRequest.builder()
                        .code(code)
                        .clientId(this.CLIENT_ID)
                        .clientSecret(this.CLIENT_SECRET)
                        .redirectUri(this.REDIRECT_URI)
                        .grantType(this.GRANT_TYPE)
                        .build()
        );

        return AuthenticationResponse.builder().token(response.getAccessToken()).build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = this.verifyToken(request.getToken(), true);

        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();
        invalidatedTokenRepository.save(invalidatedToken);

        String username = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        String token = this.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            SignedJWT signToken = this.verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException exception) {
            log.info("Token already expired");
        }
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        boolean isValid = true;

        try {
            this.verifyToken(request.getToken(), false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("harmony-supply-chain")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(this.VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", user.getRole())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(this.SIGNER_KEY.getBytes()));

            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(this.SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = isRefresh
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(this.REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (this.invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
}
