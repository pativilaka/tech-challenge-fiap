package br.com.fiap.techchallenge.application.usuario.service;

import br.com.fiap.techchallenge.application.comum.ApplicationException;
import br.com.fiap.techchallenge.application.usuario.dto.CriarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.usuario.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.application.usuario.mappers.UsuarioMapper;
import br.com.fiap.techchallenge.application.usuario.ports.in.ICriarUsuario;
import br.com.fiap.techchallenge.application.usuario.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IUsuarioPresenter;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriarUsuarioService implements ICriarUsuario {

    private final IUsuarioRepository repository;
    private final IUsuarioPresenter presenter;
    private final PasswordEncoder passwordEncoder;

    public CriarUsuarioService(IUsuarioRepository repository, IUsuarioPresenter presenter, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.presenter = presenter;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public void execute(CriarUsuarioRequestApp requestApp) {

        if (requestApp == null) throw new ApplicationException("Request não pode ser nulo!");
        CriarUsuarioRequestApp usuarioSenhaCodifica = requestApp.withEncodedSenha(passwordEncoder.encode(requestApp.senha()));

        Usuario usuario = UsuarioMapper.toDomain(usuarioSenhaCodifica);
        usuario = repository.save(usuario);

        UsuarioResponseApp responseApp = UsuarioMapper.toApp(usuario);
        presenter.created(responseApp);

    }
}
