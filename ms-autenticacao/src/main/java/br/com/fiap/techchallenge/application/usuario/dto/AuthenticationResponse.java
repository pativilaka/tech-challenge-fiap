package br.com.fiap.techchallenge.application.usuario.dto;

import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;

public record AuthenticationResponse(
        String token,
        Long id,
        String username,
        String email,
        TipoUsuario tipoUsuario) {
}
