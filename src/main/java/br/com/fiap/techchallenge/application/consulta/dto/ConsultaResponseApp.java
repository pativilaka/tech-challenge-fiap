package br.com.fiap.techchallenge.application.consulta.dto;

import br.com.fiap.techchallenge.domain.consulta.StatusConsulta;

import java.time.LocalDate;

public record ConsultaResponseApp(
        Long id,
        Long medicoId,
        Long pacienteId,
        LocalDate inicio,
        LocalDate fim,
        StatusConsulta status
) {
}
