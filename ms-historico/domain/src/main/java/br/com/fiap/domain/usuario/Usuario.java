package br.com.fiap.domain.usuario;

import br.com.fiap.domain.AggregateRoot;
import br.com.fiap.domain.exceptions.NotificationException;
import br.com.fiap.domain.validation.ValidationHandler;
import br.com.fiap.domain.validation.handler.Notification;

import java.time.LocalDate;

public class Usuario extends AggregateRoot<UsuarioID> implements Cloneable {

    private String nome;

    private CPF cpf;

    private LocalDate dataNascimento;

    private String email;

    private String senha;

    private String tipoUsuario;

    protected Usuario(
            final UsuarioID usuarioID,
            final String nome,
            final CPF cpf,
            final LocalDate dataNascimento,
            final String email,
            final String senha,
            final String tipoUsuario
    ) {
        super(usuarioID);
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        selfValidate();
    }

    protected static Usuario newUsuario(
            final String nome,
            final CPF cpf,
            final LocalDate dataNascimento,
            final String email,
            final String senha,
            final String tipoUsuario
    ) {
        final var id = UsuarioID.unique();
        return new Usuario(
                id,
                nome,
                cpf,
                dataNascimento,
                email,
                senha,
                tipoUsuario
        );
    }

    public static Usuario clone(final Usuario usuario) {
        return usuario.clone();
    }

    public static Usuario with(
            final UsuarioID usuarioID,
            final String nome,
            final String cpf,
            final LocalDate dataNascimento,
            final String email,
            final String senha,
            final String tipoUsuario
    ) {
        return new Usuario(
                usuarioID,
                nome,
                new CPF(cpf),
                dataNascimento,
                email,
                senha,
                tipoUsuario
        );
    }



    @Override
    public void validate(final ValidationHandler handler) {
        new UsuarioValidator(this, handler).validate();
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Falha ao criar objeto usuário", notification);
        }
    }

    public String getNome() {
        return nome;
    }

    public CPF getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    @Override
    public Usuario clone() {
        try {
            return (Usuario) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
