package br.com.fiap.techchallenge.infrastructure.rest.presenters;

import br.com.fiap.techchallenge.application.consulta.dto.ConsultaResponseApp;
import br.com.fiap.techchallenge.application.consulta.dto.ListaConsultasResponseApp;
import br.com.fiap.techchallenge.application.consulta.ports.presenters.IConsultaPresenter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConsultaPresenterHttp implements IConsultaPresenter {

    private ConsultaResponseApp modelo;
    private ListaConsultasResponseApp listaModelo;

    @Override
    public void present(ConsultaResponseApp result) {
        this.modelo = result;
    }

    @Override
    public void presentAll(ListaConsultasResponseApp result) {
        this.listaModelo = result;
    }

    public ConsultaResponseApp get() {
        return modelo;
    }

    public ListaConsultasResponseApp getLista() {
        return listaModelo;
    }
}
