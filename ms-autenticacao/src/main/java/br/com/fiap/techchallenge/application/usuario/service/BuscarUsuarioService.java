package br.com.fiap.techchallenge.application.usuario.service;

import br.com.fiap.techchallenge.application.usuario.mappers.UsuarioMapper;
import br.com.fiap.techchallenge.application.usuario.ports.in.IBuscarUsuario;
import br.com.fiap.techchallenge.application.usuario.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IBuscarUsuarioPresenter;
import br.com.fiap.techchallenge.domain.usuario.UserDetailsImpl;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import br.com.fiap.techchallenge.infrastructure.config.security.JWTUtils;
import jakarta.security.auth.message.AuthException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class BuscarUsuarioService implements IBuscarUsuario {

    private final IBuscarUsuarioPresenter presenter;
    private final IUsuarioRepository repository;
    private final JWTUtils jwtUtils;


    public BuscarUsuarioService(IBuscarUsuarioPresenter presenter,
                                IUsuarioRepository repository,
                                JWTUtils jwtUtils) {
        this.presenter = presenter;
        this.repository = repository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void execute(String token) throws AuthException {
        if (token != null && jwtUtils.validateJwtToken(token)) {
            String email = jwtUtils.getIdFromJwtToken(token);
            presenter.present(UsuarioMapper.toApp(getUsuarioLogadoByEmail(email)));
            return;
        }
        throw new AuthException("Nenhum usuário logado encontrado.");
    }


    public Usuario getUsuarioLogadoByEmail(String email) {

        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return UserDetailsImpl.build(getUsuarioLogadoByEmail(email));
    }
}
