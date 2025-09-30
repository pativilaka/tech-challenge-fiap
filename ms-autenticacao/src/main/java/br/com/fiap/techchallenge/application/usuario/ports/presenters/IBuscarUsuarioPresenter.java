package br.com.fiap.techchallenge.application.usuario.ports.presenters;

import br.com.fiap.techchallenge.application.usuario.dto.UsuarioResponseApp;

public interface IBuscarUsuarioPresenter {

    UsuarioResponseApp present(UsuarioResponseApp result);
}
