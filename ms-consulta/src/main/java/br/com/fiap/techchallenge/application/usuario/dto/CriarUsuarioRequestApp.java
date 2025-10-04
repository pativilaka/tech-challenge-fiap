package br.com.fiap.techchallenge.application.usuario.dto;

import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;

import java.time.LocalDate;

public record CriarUsuarioRequestApp(
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
    public CriarUsuarioRequestApp withEncodedSenha(String encodedSenha) {
        return new CriarUsuarioRequestApp(
                this.nome,
                this.cpf,
                this.dataNascimento,
                this.email,
                this.telefone,
                this.login,
                encodedSenha,
                this.endereco,
                this.tipoUsuario,
                this.crm,
                this.especialidade,
                this.coren,
                this.planoSaude
        );
    }
}
