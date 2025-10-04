package br.com.fiap.techchallenge.application.usuario.service;

import br.com.fiap.techchallenge.application.usuario.mappers.UsuarioMapper;
import br.com.fiap.techchallenge.application.usuario.ports.in.IBuscarUsuario;
import br.com.fiap.techchallenge.application.usuario.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IBuscarUsuarioPresenter;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.security.auth.message.AuthException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
@Log4j2
public class BuscarUsuarioService implements IBuscarUsuario {

    private final IBuscarUsuarioPresenter presenter;
    private final IUsuarioRepository repository;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    public BuscarUsuarioService(IBuscarUsuarioPresenter presenter,
                                IUsuarioRepository repository) {
        this.presenter = presenter;
        this.repository = repository;
    }

    @Override
    public void execute(String token) throws AuthException {
        if (token != null && this.validateJwtToken(token)) {
            String username = this.getUserNameFromJwtToken(token);
            getUsuarioLogadoByEmail(username);
            return;
        }
        throw new AuthException("Nenhum usuário logado encontrado.");
    }

    public String getUserNameFromJwtToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(authToken);

            return true;
        } catch (Exception e) {
            log.error("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }

    private void getUsuarioLogadoByEmail(String email) {
        final Usuario usuarioEntidade = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        presenter.present(UsuarioMapper.toApp(usuarioEntidade));
    }
}
