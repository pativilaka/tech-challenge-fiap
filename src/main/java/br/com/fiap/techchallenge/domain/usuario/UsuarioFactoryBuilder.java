package br.com.fiap.techchallenge.domain.usuario;

import br.com.fiap.techchallenge.domain.comum.Endereco;

import java.time.LocalDate;

public final class UsuarioFactoryBuilder {

    private Long id; // opcional (pode vir do banco)
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private String login;
    private String senha;
    private Endereco endereco;
    private TipoUsuario tipoUsuario;

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

    public Usuario build() {
        return new Usuario(
                id,
                nome,
                cpf,
                dataNascimento,
                email,
                telefone,
                login,
                senha,
                endereco,
                tipoUsuario
        );
    }
}
