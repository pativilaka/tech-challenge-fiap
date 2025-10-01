package br.com.fiap.techchallenge.infrastructure.rest.presenters;

import br.com.fiap.techchallenge.application.usuario.dto.AuthenticationResponse;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IAutenticacaoPresenter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AutenticacaoPresenterHttp implements IAutenticacaoPresenter {

    private AuthenticationResponse modelo;

    @Override
    public AuthenticationResponse present(AuthenticationResponse result) {
        this.modelo = result;
        return result;
    }

    public AuthenticationResponse get() {
        return modelo;
    }

}
