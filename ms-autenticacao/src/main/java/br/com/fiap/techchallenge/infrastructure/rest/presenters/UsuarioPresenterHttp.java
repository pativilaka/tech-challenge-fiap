package br.com.fiap.techchallenge.infrastructure.rest.presenters;

import br.com.fiap.techchallenge.application.usuario.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IBuscarUsuarioPresenter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UsuarioPresenterHttp implements IBuscarUsuarioPresenter {

    private UsuarioResponseApp modelo;

    @Override
    public UsuarioResponseApp present(UsuarioResponseApp result) {
        this.modelo = result;
        return result;
    }

    public UsuarioResponseApp get() {
        return modelo;
    }

}
