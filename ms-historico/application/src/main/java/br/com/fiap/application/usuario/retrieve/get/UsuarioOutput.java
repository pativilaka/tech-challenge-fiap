package br.com.fiap.application.usuario.retrieve.get;

import br.com.fiap.domain.usuario.CPF;
import br.com.fiap.domain.usuario.Usuario;
import br.com.fiap.domain.usuario.UsuarioID;

public record UsuarioOutput(
        UsuarioID id,
        String nome,
        CPF cpf,
        String dataNascimento,
        String email,
        String tipoUsuario
) {
    public static UsuarioOutput from(final Usuario aUsuario) {
        return new UsuarioOutput(
                aUsuario.getId(),
                aUsuario.getNome(),
                aUsuario.getCpf(),
                aUsuario.getDataNascimento().toString(),
                aUsuario.getEmail(),
                aUsuario.getTipoUsuario()
        );
    }
}
