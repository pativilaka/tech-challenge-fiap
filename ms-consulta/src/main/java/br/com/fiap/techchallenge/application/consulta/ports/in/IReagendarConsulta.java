package br.com.fiap.techchallenge.application.consulta.ports.in;

import br.com.fiap.techchallenge.application.consulta.dto.ReagendarConsultaRequestApp;

public interface IReagendarConsulta {
    void execute(ReagendarConsultaRequestApp req);
}
