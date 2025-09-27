package br.com.fiap.infraestructure.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;

@Component
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfig {

    private static final String SECRET = "suaChaveSuperSecretaDeNoMinimo32Caracteres";


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(conf -> conf.jwt(Customizer.withDefaults()));

        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        byte[] secretBytes = SECRET.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretBytes, "AES");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }

    @Bean
    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("roles"); // pega do claim roles
        authoritiesConverter.setAuthorityPrefix("ROLE_"); // adiciona prefixo ROLE_

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }

}
