package br.com.fiap.techchallenge.domain.usuario;

import br.com.fiap.techchallenge.domain.comum.DomainValidation;
import br.com.fiap.techchallenge.domain.comum.Endereco;

import java.time.LocalDate;

public class Paciente extends Usuario{
    private String planoSaude;

    Paciente(Long id, String nome, String cpf, LocalDate dataNascimento,
             String email, String telefone, String login, String senha,
             Endereco endereco, String planoSaude) {
        super(id, nome, cpf, dataNascimento, email, telefone, login, senha, endereco, TipoUsuario.PACIENTE);
        this.planoSaude = planoSaude;
        DomainValidation.notBlank(this.planoSaude, "planoSaude");
    }

    public String getPlanoSaude() {
        return planoSaude;
    }

    public void alterarPlanoSaude(String novo) {
        DomainValidation.notBlank(novo, "planoSaude");
        this.planoSaude = novo;
    }
}
