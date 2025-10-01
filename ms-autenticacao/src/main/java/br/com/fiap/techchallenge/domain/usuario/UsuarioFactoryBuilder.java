package br.com.fiap.techchallenge.domain.usuario;

import br.com.fiap.techchallenge.domain.comum.DomainException;
import br.com.fiap.techchallenge.domain.comum.DomainValidation;
import br.com.fiap.techchallenge.domain.comum.Endereco;

import java.time.LocalDate;

public final class UsuarioFactoryBuilder {

    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private String login;
    private String senha;
    private Endereco endereco;
    private TipoUsuario tipoUsuario;

    private String planoSaude;
    private String coren;
    private String crm;
    private String especialidade;

    private UsuarioFactoryBuilder() {}

    public static UsuarioFactoryBuilder novo() {
        return new UsuarioFactoryBuilder();
    }

    public UsuarioFactoryBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public UsuarioFactoryBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public UsuarioFactoryBuilder cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public UsuarioFactoryBuilder dataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public UsuarioFactoryBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UsuarioFactoryBuilder telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public UsuarioFactoryBuilder login(String login) {
        this.login = login;
        return this;
    }

    public UsuarioFactoryBuilder senha(String senha) {
        this.senha = senha;
        return this;
    }

    public UsuarioFactoryBuilder endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public UsuarioFactoryBuilder tipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        return this;
    }

    public UsuarioFactoryBuilder planoSaude(String planoSaude) {
        this.planoSaude = planoSaude;
        return this;
    }

    public UsuarioFactoryBuilder coren(String coren) {
        this.coren = coren;
        return this;
    }

    public UsuarioFactoryBuilder crm(String crm) {
        this.crm = crm;
        return this;
    }

    public UsuarioFactoryBuilder especialidade(String especialidade) {
        this.especialidade = especialidade;
        return this;
    }

    public Usuario build() {

        DomainValidation.notNull(tipoUsuario, "tipoUsuario");

        switch (tipoUsuario) {
            case PACIENTE:
                if (crm != null || coren != null)
                    throw new DomainException("crm/coren não se aplicam a PACIENTE");
                return new Paciente(id, nome, cpf, dataNascimento, email, telefone, login, senha, endereco, planoSaude);

            case ENFERMEIRO:
                if (crm != null)
                    throw new DomainException("crm não se aplica a ENFERMEIRA");
                return new Enfermeiro(id, nome, cpf, dataNascimento, email, telefone, login, senha, endereco, coren);

            case MEDICO:
                if (coren != null)
                    throw new DomainException("coren não se aplica a MEDICO");
                return new Medico(id, nome, cpf, dataNascimento, email, telefone, login, senha, endereco, crm, especialidade);

            default:
                throw new DomainException("Tipo de usuário não suportado: " + tipoUsuario);
        }
    }
}
