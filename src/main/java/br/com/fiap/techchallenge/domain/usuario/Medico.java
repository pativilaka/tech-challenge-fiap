package br.com.fiap.techchallenge.domain.usuario;

import br.com.fiap.techchallenge.domain.comum.DomainValidation;
import br.com.fiap.techchallenge.domain.comum.Endereco;

import java.time.LocalDate;

public class Medico extends Usuario{

    private String crm;
    private String especialidade;

    Medico(Long id, String nome, String cpf, LocalDate dataNascimento,
           String email, String telefone, String login, String senha,
           Endereco endereco, String crm, String especialidade) {
        super(id, nome, cpf, dataNascimento, email, telefone, login, senha, endereco, TipoUsuario.MEDICO);
        this.crm = crm;
        this.especialidade = especialidade;
        DomainValidation.notBlank(this.crm, "crm");
        DomainValidation.notBlank(this.especialidade, "especialidade");
    }

    public String getCrm() {
        return crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void alterarCrm(String novo) {
        DomainValidation.notBlank(novo, "crm");
        this.crm = novo;
    }

    public void alterarEspecialidade(String nova) {
        DomainValidation.notBlank(nova, "especialidade");
        this.especialidade = nova;
    }
}
