package br.com.fiap.techchallenge.application.service;

import br.com.fiap.techchallenge.application.comum.ApplicationException;
import br.com.fiap.techchallenge.application.comum.NotFoundException;
import br.com.fiap.techchallenge.application.ports.in.IDeletarUsuario;
import br.com.fiap.techchallenge.application.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.ports.presenters.IUsuarioPresenter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletarUsuarioService implements IDeletarUsuario {

    private final IUsuarioRepository repository;
    private final IUsuarioPresenter presenter;

    public DeletarUsuarioService(IUsuarioRepository repository, IUsuarioPresenter presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    @Transactional
    @Override
    public void execute(Long id) {
        if(id == null) throw new ApplicationException("Id não pode ser nulo");

        boolean deletado = repository.deleteById(id);
        if(!deletado) throw new NotFoundException("Usuário %d não encontrado.".formatted(id));

        presenter.deleted(id);

    }
}
