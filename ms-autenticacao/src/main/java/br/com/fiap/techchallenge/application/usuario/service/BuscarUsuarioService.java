package br.com.fiap.techchallenge.application.usuario.service;

import br.com.fiap.techchallenge.application.usuario.mappers.UsuarioMapper;
import br.com.fiap.techchallenge.application.usuario.ports.in.IBuscarUsuario;
import br.com.fiap.techchallenge.application.usuario.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IBuscarUsuarioPresenter;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import io.jsonwebtoken.*;
import jakarta.security.auth.message.AuthException;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class BuscarUsuarioService implements IBuscarUsuario {

    private static final Logger logger = LoggerFactory.getLogger(BuscarUsuarioService.class);

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
        }
        throw new AuthException("Nenhum usuário logado encontrado.");
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody().getSubject();
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private void getUsuarioLogadoByEmail(String email) {
        final Usuario usuarioEntidade = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        presenter.present(UsuarioMapper.toApp(usuarioEntidade));
    }
}
