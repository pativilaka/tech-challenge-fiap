package br.com.fiap.techchallenge.application.service;

import br.com.fiap.techchallenge.application.dto.ListaUsuariosResponseApp;
import br.com.fiap.techchallenge.application.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.application.mappers.UsuarioMapper;
import br.com.fiap.techchallenge.application.ports.in.IListarUsuarios;
import br.com.fiap.techchallenge.application.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.ports.presenters.IUsuarioPresenter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListarUsuariosService implements IListarUsuarios {

    private final IUsuarioRepository repository;
    private final IUsuarioPresenter presenter;

    public ListarUsuariosService(IUsuarioRepository repository, IUsuarioPresenter presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    @Transactional(readOnly = true)
    @Override
    public void execute() {

        List<UsuarioResponseApp> listaUsuarios = repository.findAll()
                .stream()
                .map(UsuarioMapper::toApp)
                .toList();

        presenter.presentAll(new ListaUsuariosResponseApp(listaUsuarios));

    }
}
