package br.com.fiap.techchallenge.application.consulta.dto;

import java.time.LocalDateTime;

public record ReagendarConsultaRequestApp(
        Long consultaId,
        LocalDateTime novoInicio,
        LocalDateTime novoFim
) {
}
