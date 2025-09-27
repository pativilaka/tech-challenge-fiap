package br.com.fiap.infraestructure.consulta.model;


import br.com.fiap.domain.consulta.Consulta;
import br.com.fiap.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record ConsultaResponse(
        String id,
        LocalDateTime dataHora,
        UsuarioResponse paciente,
        UsuarioResponse medico,
        String status
) {


    public record UsuarioResponse(
            String id,
            String nome,
            String email
    ) {
    }
}
