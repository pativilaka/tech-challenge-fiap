package br.com.fiap.techchallenge.application.consulta.dto;

import java.time.LocalDate;

public record AgendarConsultaRequestApp(
        Long medicoId,
        Long pacienteId,
        LocalDate inicio,
        LocalDate fim
) {
}
