package br.com.fiap.techchallenge.application.consulta.ports.out;

import br.com.fiap.techchallenge.domain.consulta.Consulta;

public interface IConsultaProducer {
    void enviarEventoConsulta(Consulta consulta);
}
