package br.com.fiap.msnotificacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EventNotificationEmailDTO(
        @JsonProperty("id_consulta")
        int idConsulta,
        String email,
        @JsonProperty("nome_doutor")
        String nomeDoutor,
        @JsonProperty("nome_paciente")
        String nomePaciente,
        @JsonProperty("data_hora_consulta")
        String dataHoraConsulta,
        @JsonProperty("tipo_evento")
        Status tipoEvento
) {
}
