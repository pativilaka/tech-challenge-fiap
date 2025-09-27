package br.com.fiap.techchallenge.application.usuario.service;

import br.com.fiap.techchallenge.application.comum.ApplicationException;
import br.com.fiap.techchallenge.application.comum.NotFoundException;
import br.com.fiap.techchallenge.application.usuario.mappers.UsuarioMapper;
import br.com.fiap.techchallenge.application.usuario.ports.in.IBuscarUsuarioPorId;
import br.com.fiap.techchallenge.application.usuario.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IUsuarioPresenter;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuscarUsuarioPorIdService implements IBuscarUsuarioPorId {

    private final IUsuarioRepository repository;
    private final IUsuarioPresenter presenter;

    public BuscarUsuarioPorIdService(IUsuarioRepository repository, IUsuarioPresenter presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    @Transactional(readOnly = true)
    @Override
    public void execute(Long id) {

        if (id == null) throw new ApplicationException("Id não pode ser nulo.");

        Usuario usuario = repository.findById(id).orElseThrow(() -> new NotFoundException(
                "Usuário %d não encontrado.".formatted(id)));

        presenter.present(UsuarioMapper.toApp(usuario));

    }
}
