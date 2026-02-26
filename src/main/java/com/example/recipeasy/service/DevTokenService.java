package com.example.recipeasy.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Service exclusivo para geração de tokens de desenvolvimento.
 * Só é carregado quando o profile "dev" está ativo.
 * NÃO existe em produção.
 */
@Service
@Profile("dev")
public class DevTokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateDevToken() {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("recipeasy-api")
                    .withSubject("dev-admin")
                    .withClaim("dev", true)
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT de desenvolvimento", exception);
        }
    }

    public boolean isDevToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var decodedJWT = JWT.require(algorithm)
                    .withIssuer("recipeasy-api")
                    .build()
                    .verify(token);
            var devClaim = decodedJWT.getClaim("dev");
            return !devClaim.isNull() && devClaim.asBoolean();
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
