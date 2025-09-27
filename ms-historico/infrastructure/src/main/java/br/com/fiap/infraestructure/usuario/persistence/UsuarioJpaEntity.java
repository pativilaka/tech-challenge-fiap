package br.com.fiap.infraestructure.usuario.persistence;

import br.com.fiap.domain.usuario.Usuario;
import br.com.fiap.domain.usuario.UsuarioID;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
public class UsuarioJpaEntity {

    public UsuarioJpaEntity() {}

    private UsuarioJpaEntity(
            final String id,
            final String nome,
            final String cpf,
            final LocalDate dataNascimento,
            final String email,
            final String senha,
            final TipoUsuario tipoUsuario
    ) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    public static UsuarioJpaEntity from(Usuario usuario) {
        return new UsuarioJpaEntity(
                usuario.getId().getValue(),
                usuario.getNome(),
                usuario.getCpf().getValue(),
                usuario.getDataNascimento(),
                usuario.getEmail(),
                usuario.getSenha(),
                TipoUsuario.valueOf(usuario.getTipoUsuario())
        );
    }

    public Usuario toAggregate() {
        return Usuario.with(
                UsuarioID.from(this.id),
                this.nome,
                this.cpf,
                this.dataNascimento,
                this.email,
                this.senha,
                this.tipoUsuario.name()
        );
    }

    @Id
    private String id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
