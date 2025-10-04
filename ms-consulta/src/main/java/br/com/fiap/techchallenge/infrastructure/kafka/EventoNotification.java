package br.com.fiap.techchallenge.infrastructure.kafka;

import br.com.fiap.techchallenge.domain.consulta.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EventoNotification(
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
        StatusConsulta tipoEvento
) {
}
