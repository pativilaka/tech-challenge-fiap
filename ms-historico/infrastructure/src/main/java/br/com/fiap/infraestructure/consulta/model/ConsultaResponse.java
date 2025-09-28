package br.com.fiap.infraestructure.consulta.model;

import java.time.LocalDateTime;

public record ConsultaResponse(
        Integer id,
        LocalDateTime dataHora,
        UsuarioResponse paciente,
        UsuarioResponse medico,
        String status
) {


    public record UsuarioResponse(
            Integer id,
            String nome,
            String email
    ) {
    }
}
