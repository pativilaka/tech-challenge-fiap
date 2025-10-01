package br.com.fiap.domain.enfermeiro;

import br.com.fiap.domain.usuario.CPF;
import br.com.fiap.domain.usuario.Usuario;
import br.com.fiap.domain.usuario.UsuarioID;

import java.time.LocalDate;

public class Enfermeiro extends Usuario {

    private final String coren;

    protected Enfermeiro(final UsuarioID usuarioID,
                         final String nome,
                         final CPF cpf,
                         final LocalDate dataNascimento,
                         final String email,
                         final String senha,
                         final String coren) {
        super(usuarioID, nome, cpf, dataNascimento, email, senha, "ENFERMEIRO");
        this.coren = coren;
    }

    public static Enfermeiro newPaciente(final UsuarioID id,
                                        final String nome,
                                         final CPF cpf,
                                         final LocalDate dataNascimento,
                                         final String email,
                                         final String senha,
                                         final String coren) {
        return new Enfermeiro(id, nome, cpf, dataNascimento, email, senha, coren);
    }

    public String getCoren() {
        return coren;
    }
}
