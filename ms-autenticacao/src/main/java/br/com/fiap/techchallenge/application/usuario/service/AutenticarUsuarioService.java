package br.com.fiap.techchallenge.application.usuario.service;

import br.com.fiap.techchallenge.application.usuario.dto.AuthenticationResponse;
import br.com.fiap.techchallenge.application.usuario.ports.in.IAutenticarUsuario;
import br.com.fiap.techchallenge.application.usuario.dto.LoginRequestApp;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IAutenticacaoPresenter;
import br.com.fiap.techchallenge.domain.usuario.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class AutenticarUsuarioService implements IAutenticarUsuario {

    private final IAutenticacaoPresenter presenter;
    private final AuthenticationManager authenticationManager;

    @Value("${app.jwtSecret}")
    public String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    public int jwtExpirationMs;

    public AutenticarUsuarioService(IAutenticacaoPresenter presenter,
                                    AuthenticationManager authenticationManager) {
        this.presenter = presenter;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void execute(LoginRequestApp requestApp) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestApp.login(), requestApp.senha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = this.generateJwtToken(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        presenter.present(new AuthenticationResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getTipoUsuario()));

    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
