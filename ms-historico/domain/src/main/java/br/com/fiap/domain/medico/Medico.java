package br.com.fiap.domain.medico;

import br.com.fiap.domain.usuario.CPF;
import br.com.fiap.domain.usuario.Usuario;
import br.com.fiap.domain.usuario.UsuarioID;

import java.time.LocalDate;

public class Medico extends Usuario {

    private final String crm;

    private final String especialidade;

    protected Medico(final UsuarioID usuarioID,
                     final String nome,
                     final CPF cpf,
                     final LocalDate dataNascimento,
                     final String email,
                     final String senha,
                     final String crm,
                     final String especialidade) {
        super(usuarioID, nome, cpf, dataNascimento, email, senha, "MEDICO");
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public static Medico newMedico(
            final UsuarioID id,
            final String nome,
            final CPF cpf,
            final LocalDate dataNascimento,
            final String email,
            final String senha,
            final String crm,
            final String especialidade) {
        return new Medico(id, nome, cpf, dataNascimento, email, senha, crm, especialidade);
    }

    public String getCrm() {
        return crm;
    }

    public String getEspecialidade() {return especialidade;}
}
