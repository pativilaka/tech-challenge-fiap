package br.com.fiap.techchallenge.application.usuario.dto;

import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;

import java.time.LocalDate;

public record UsuarioResponseApp(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String email,
        String telefone,
        String login,
        EnderecoApp endereco,
        TipoUsuario tipoUsuario,
        String crm,
        String especialidade,
        String coren,
        String planoSaude
) {


}
