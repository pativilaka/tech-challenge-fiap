package br.com.fiap.techchallenge.domain.usuario;

import br.com.fiap.techchallenge.domain.comum.DomainValidation;
import br.com.fiap.techchallenge.domain.comum.Endereco;

import java.time.LocalDate;
import java.util.Objects;

public class Usuario {

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

    Usuario(
            Long id,
            String nome,
            String cpf,
            LocalDate dataNascimento,
            String email,
            String telefone,
            String login,
            String senha,
            Endereco endereco,
            TipoUsuario tipoUsuario
    ) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
        this.login = login;
        this.senha = senha;
        this.endereco = endereco;
        this.tipoUsuario = tipoUsuario;
        validar();
    }

    private void validar() {
        DomainValidation.notBlank(nome, "nome");
        DomainValidation.cpf(cpf, "cpf");
        DomainValidation.notNull(dataNascimento, "dataNascimento");
        DomainValidation.email(email, "email");
        DomainValidation.telefone(telefone, "telefone");
        DomainValidation.minLength(senha, 6, "senha");
        DomainValidation.notNull(tipoUsuario, "tipo do usuário");
    }

    public void alterarNome(String novoNome) {
        DomainValidation.notBlank(novoNome, "nome");
        this.nome = novoNome;
    }

    public void alterarEmail(String novoEmail) {
        DomainValidation.email(novoEmail, "email");
        this.email = novoEmail;
    }

    public void alterarTelefone(String novoTelefone) {
        DomainValidation.telefone(novoTelefone, "telefone");
        this.telefone = novoTelefone;
    }

    public void alterarSenha(String novaSenha) {
        DomainValidation.minLength(novaSenha, 6, "senha");
        this.senha = novaSenha;
    }

    public void alterarEndereco(Endereco novoEndereco) {
        DomainValidation.notNull(novoEndereco, "endereco");
        this.endereco = novoEndereco;
    }

    public void alterarTipoUsuario(TipoUsuario novoTipo) {
        DomainValidation.notNull(novoTipo, "tipoUsuario");
        this.tipoUsuario = novoTipo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
