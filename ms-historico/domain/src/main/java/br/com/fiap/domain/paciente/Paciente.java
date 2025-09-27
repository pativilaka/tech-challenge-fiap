package br.com.fiap.domain.paciente;

import br.com.fiap.domain.usuario.CPF;
import br.com.fiap.domain.usuario.Usuario;
import br.com.fiap.domain.usuario.UsuarioID;

import java.time.LocalDate;

public class Paciente extends Usuario {

    private final String convenioMedico;

    protected Paciente(UsuarioID usuarioID, String nome, CPF cpf, LocalDate dataNascimento, String email, String senha, String convenioMedico) {
        super(usuarioID, nome, cpf, dataNascimento, email, senha, "PACIENTE");
        this.convenioMedico = convenioMedico;
    }

    public static Paciente newPaciente(String nome, CPF cpf, LocalDate dataNascimento, String email, String senha, String convenioMedico) {
        final var id = UsuarioID.unique();
        return new Paciente(id, nome, cpf, dataNascimento, email, senha, convenioMedico);
    }

    public String getConvenioMedico() {
        return convenioMedico;
    }
}
