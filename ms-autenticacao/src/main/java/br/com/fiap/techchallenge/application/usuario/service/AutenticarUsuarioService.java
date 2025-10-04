package br.com.fiap.techchallenge.application.usuario.service;

import br.com.fiap.techchallenge.application.usuario.dto.AuthenticationResponse;
import br.com.fiap.techchallenge.application.usuario.ports.in.IAutenticarUsuario;
import br.com.fiap.techchallenge.application.usuario.dto.LoginRequestApp;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IAutenticacaoPresenter;
import br.com.fiap.techchallenge.domain.usuario.UserDetailsImpl;
import br.com.fiap.techchallenge.infrastructure.config.security.JWTUtils;
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
    private final JWTUtils jwtUtils;

    public AutenticarUsuarioService(IAutenticacaoPresenter presenter,
                                    AuthenticationManager authenticationManager,
                                    JWTUtils jwtUtils) {
        this.presenter = presenter;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void execute(LoginRequestApp requestApp) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestApp.login(), requestApp.senha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = jwtUtils.generateJwtToken(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        presenter.present(new AuthenticationResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getTipoUsuario()));

    }


}
