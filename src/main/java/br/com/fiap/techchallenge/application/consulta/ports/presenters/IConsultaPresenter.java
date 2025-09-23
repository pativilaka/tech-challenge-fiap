package br.com.fiap.techchallenge.application.consulta.ports.presenters;

import br.com.fiap.techchallenge.application.consulta.dto.ConsultaResponseApp;
import br.com.fiap.techchallenge.application.consulta.dto.ListaConsultasResponseApp;

public interface IConsultaPresenter {
    void present(ConsultaResponseApp responseApp);
    void presentAll(ListaConsultasResponseApp responseApp);
}
