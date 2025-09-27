package br.com.fiap.techchallenge.infrastructure.rest.presenters;

import br.com.fiap.techchallenge.application.usuario.dto.ListaUsuariosResponseApp;
import br.com.fiap.techchallenge.application.usuario.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IUsuarioPresenter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UsuarioPresenterHttp implements IUsuarioPresenter {

    private UsuarioResponseApp modelo;
    private ListaUsuariosResponseApp listaModelo;
    private Long deletedId;

    @Override
    public void present(UsuarioResponseApp result) {
        this.modelo = result;
    }

    @Override
    public void presentAll(ListaUsuariosResponseApp result) {
        this.listaModelo = result;
    }

    @Override
    public void deleted(Long id) {
        this.deletedId = id;
    }

    @Override
    public void created(UsuarioResponseApp result) {
        this.modelo = result;
    }

    public UsuarioResponseApp get() {
        return modelo;
    }

    public ListaUsuariosResponseApp getLista() {
        return listaModelo;
    }

    public Long getId() {
        return deletedId;
    }
}
