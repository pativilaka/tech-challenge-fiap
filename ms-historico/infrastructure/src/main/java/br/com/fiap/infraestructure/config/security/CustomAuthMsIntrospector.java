package br.com.fiap.infraestructure.config.security;

import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CustomAuthMsIntrospector implements OpaqueTokenIntrospector {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String introspectUri;

    public CustomAuthMsIntrospector(String introspectUri) {
        this.introspectUri = introspectUri;
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response;
        try {
            response = restTemplate.exchange(introspectUri, HttpMethod.GET, entity, Map.class);
        } catch (Exception e) {
            throw new OAuth2IntrospectionException("Erro ao validar token no Auth MS", e);
        }

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new OAuth2IntrospectionException("Token inválido ou resposta vazia do Auth MS");
        }

        Map<String, Object> claims = response.getBody();

        // ⚠️ aqui você decide como extrair username e authorities
        String name = (String) claims.getOrDefault("sub", "anonymous");
        String role = claims.getOrDefault("tipoUsuario", "USER").toString();
        Collection<GrantedAuthority> authorities = List.of(() -> "ROLE_" + role.toUpperCase());

        return new DefaultOAuth2AuthenticatedPrincipal(name, claims, authorities);
    }
}
