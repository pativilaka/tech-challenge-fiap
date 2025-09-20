package br.com.fiap.techchallenge.application.dto;

import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;

import java.time.LocalDate;

public record AtualizarUsuarioRequestApp(
        Long id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        String email,
        String telefone,
        String login,
        String senha,
        EnderecoApp endereco,
        TipoUsuario tipoUsuario,
        String crm,
        String especialidade,
        String coren,
        String planoSaude
) {
}
