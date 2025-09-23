package br.com.fiap.techchallenge.application.usuario.service;

import br.com.fiap.techchallenge.application.comum.ApplicationException;
import br.com.fiap.techchallenge.application.comum.NotFoundException;
import br.com.fiap.techchallenge.application.usuario.dto.AtualizarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.usuario.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.application.usuario.mappers.UsuarioMapper;
import br.com.fiap.techchallenge.application.usuario.ports.in.IAtualizarUsuario;
import br.com.fiap.techchallenge.application.usuario.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IUsuarioPresenter;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarUsuarioService implements IAtualizarUsuario {

    private final IUsuarioRepository repository;
    private final IUsuarioPresenter presenter;

    public AtualizarUsuarioService(IUsuarioRepository repository, IUsuarioPresenter presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    @Transactional
    @Override
    public void execute(Long id, AtualizarUsuarioRequestApp requestApp) {

        if(requestApp == null) throw new ApplicationException("Request não poode ser nulo!");

        Usuario usuario = repository.findById(requestApp.id())
                .orElseThrow(() -> new NotFoundException("Usuário %d não encontrado.".formatted(requestApp.id())));

        UsuarioMapper.toUpdateDomain(requestApp, usuario);
        usuario = repository.update(usuario);

        UsuarioResponseApp responseApp = UsuarioMapper.toApp(usuario);
        presenter.present(responseApp);

    }
}
