package br.com.fiap.techchallenge.infrastructure.config.security;

import br.com.fiap.techchallenge.domain.usuario.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Log4j2
@Component
public class JWTUtils {

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private final SecretKey jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .subject(userPrincipal.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .claim("id", userPrincipal.getId().toString())
                .claim("roles", userPrincipal.getAuthorities())
                .signWith(jwtSecretKey)
                .compact();
    }

    public String getIdFromJwtToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {

            Jwts.parser()
                    .verifyWith(jwtSecretKey)
                    .build()
                    .parseSignedClaims(authToken);

            return true;
        } catch (Exception e) {
            log.error("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }

}
