package br.com.fiap.techchallenge.application.consulta.dto;

import br.com.fiap.techchallenge.domain.consulta.StatusConsulta;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ConsultaResponseApp(
        Long id,
        Long medicoId,
        Long pacienteId,
        LocalDateTime inicio,
        LocalDateTime fim,
        StatusConsulta status
) {
}
