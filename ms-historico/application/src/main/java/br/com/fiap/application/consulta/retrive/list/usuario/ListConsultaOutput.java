package br.com.fiap.application.consulta.retrive.list.usuario;

import br.com.fiap.domain.consulta.Consulta;
import br.com.fiap.domain.consulta.ConsultaID;
import br.com.fiap.domain.usuario.UsuarioID;

import java.time.LocalDateTime;

public record ListConsultaOutput(
        ConsultaID id,
        UsuarioID pacienteId,
        UsuarioID medicoId,
        LocalDateTime dataHora,
        String status
) {
    public static ListConsultaOutput from(final Consulta consulta) {
        return new ListConsultaOutput(
                consulta.getId(),
                consulta.getPacienteId(),
                consulta.getMedicoId(),
                consulta.getdataHoraConsulta(),
                consulta.getStatus()
        );
    }

}
