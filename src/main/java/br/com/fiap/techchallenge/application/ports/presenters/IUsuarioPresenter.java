package br.com.fiap.techchallenge.application.ports.presenters;

import br.com.fiap.techchallenge.application.dto.ListaUsuariosResponseApp;
import br.com.fiap.techchallenge.application.dto.UsuarioResponseApp;

public interface IUsuarioPresenter {

    void present(UsuarioResponseApp result);
    void presentAll(ListaUsuariosResponseApp result);
    void deleted(Long id);
    void created(UsuarioResponseApp result);
}
