package br.com.fiap.techchallenge.application.usuario.ports.presenters;

import br.com.fiap.techchallenge.application.usuario.dto.ListaUsuariosResponseApp;
import br.com.fiap.techchallenge.application.usuario.dto.UsuarioResponseApp;

public interface IUsuarioPresenter {

    void present(UsuarioResponseApp result);
    void presentAll(ListaUsuariosResponseApp result);
    void deleted(Long id);
    void created(UsuarioResponseApp result);
}
