package br.com.fiap.techchallenge.application.consulta.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AgendarConsultaRequestApp(
        Long medicoId,
        Long pacienteId,
        LocalDateTime inicio,
        LocalDateTime fim
) {
}
