package br.com.fiap.techchallenge.application.consulta.dto;

import java.time.LocalDate;

public record ReagendarConsultaRequestApp(
        Long consultaId,
        LocalDate novoInicio,
        LocalDate novoFim
) {
}
