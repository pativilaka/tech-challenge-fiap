package br.com.fiap.techchallenge.application.consulta.ports.in;

import br.com.fiap.techchallenge.application.consulta.dto.AgendarConsultaRequestApp;

public interface IAgendarConsulta {
    void execute(AgendarConsultaRequestApp req);
}
