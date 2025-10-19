package com.lex.Car.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public JwtTokenService(JwtEncoder encoder, JwtDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("scope",scope)
                .build();

        var encoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(),
                claims
        );

        return this.encoder.encode(encoderParameters).getTokenValue();
    }

    public Long extractExpiration(String token) {
        Jwt jwt = decoder.decode(token);
        Instant exp = jwt.getExpiresAt();
        return exp != null ? exp.toEpochMilli() : null;
    }

    public String extractUsername(String token) {
        Jwt jwt = decoder.decode(token);
        return jwt.getSubject();
    }
}