package br.com.fiap.domain.usuario;

import br.com.fiap.domain.enfermeiro.Enfermeiro;
import br.com.fiap.domain.medico.Medico;
import br.com.fiap.domain.paciente.Paciente;

import java.time.LocalDate;

public class UsuarioFactory {
    public static Usuario createUsuario(
            String nome,
            CPF cpf,
            LocalDate dataNascimento,
            String email,
            String senha,
            String tipoUsuario,
            String crm,              // para Medico
            String especialidade,    // para Medico
            String coren,            // para Enfermeiro
            String convenioMedico    // para Paciente
    ) {
        return switch (tipoUsuario.toUpperCase()) {
            case "MEDICO" -> {
                if (crm == null || especialidade == null)
                    throw new IllegalArgumentException("CRM e especialidade são obrigatórios para médico.");
                yield Medico.newMedico(nome, cpf, dataNascimento, email, senha, crm, especialidade);
            }
            case "ENFERMEIRO" -> {
                if (coren == null)
                    throw new IllegalArgumentException("Coren é obrigatório para enfermeiro.");
                yield Enfermeiro.newPaciente(nome, cpf, dataNascimento, email, senha, coren);
            }
            case "PACIENTE" -> {
                if (convenioMedico == null)
                    throw new IllegalArgumentException("Convênio médico é obrigatório para paciente.");
                yield Paciente.newPaciente(nome, cpf, dataNascimento, email, senha, convenioMedico);
            }
            default -> Usuario.newUsuario(nome, cpf, dataNascimento, email, senha, tipoUsuario);
        };
    }
}
