package com.lex.Car.util;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Bean
    public JwtEncoder jwtEncoder() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("JWT secret key is not configured!");
        }
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey.getBytes()));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("JWT secret key is not configured!");
        }
        byte[] bytes = secretKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
}
