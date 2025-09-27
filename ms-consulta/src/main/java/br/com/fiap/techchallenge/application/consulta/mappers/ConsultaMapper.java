package br.com.fiap.techchallenge.application.consulta.mappers;

import br.com.fiap.techchallenge.application.consulta.dto.AgendarConsultaRequestApp;
import br.com.fiap.techchallenge.application.consulta.dto.ConsultaResponseApp;
import br.com.fiap.techchallenge.application.consulta.dto.ListaConsultasResponseApp;
import br.com.fiap.techchallenge.domain.consulta.Consulta;

import java.util.List;

public final class ConsultaMapper {

    private ConsultaMapper() {}

    public static ConsultaResponseApp toApp(Consulta consulta){
        return new ConsultaResponseApp(
                consulta.getId(),
                consulta.getMedicoId(),
                consulta.getPacienteId(),
                consulta.getInicio(),
                consulta.getFim(),
                consulta.getStatus()
        );
    }

    public static Consulta toDomain(AgendarConsultaRequestApp requestApp) {
        return Consulta.agendar(
                null,
                requestApp.medicoId(),
                requestApp.pacienteId(),
                requestApp.inicio(),
                requestApp.fim()
        );
    }

    public static ListaConsultasResponseApp listarConsultaApp(List<Consulta> lista) {
        return new ListaConsultasResponseApp(
                lista.stream().map(ConsultaMapper::toApp).toList()
        );
    }
}
