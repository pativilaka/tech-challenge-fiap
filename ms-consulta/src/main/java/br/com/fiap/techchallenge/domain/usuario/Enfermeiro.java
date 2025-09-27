package br.com.fiap.techchallenge.domain.usuario;


import br.com.fiap.techchallenge.domain.comum.DomainValidation;
import br.com.fiap.techchallenge.domain.comum.Endereco;

import java.time.LocalDate;

public class Enfermeiro extends Usuario {

    private String coren;

    Enfermeiro(Long id, String nome, String cpf, LocalDate dataNascimento,
                    String email, String telefone, String login, String senha,
                    Endereco endereco, String coren) {
        super(id, nome, cpf, dataNascimento, email, telefone, login, senha, endereco, TipoUsuario.ENFERMEIRO);
        this.coren = coren;
        DomainValidation.notBlank(this.coren, "coren");
    }

    public String getCoren() {
        return coren;
    }

    public void alterarCoren(String novo) {
        DomainValidation.notBlank(novo, "coren");
        this.coren = novo;
    }
}
