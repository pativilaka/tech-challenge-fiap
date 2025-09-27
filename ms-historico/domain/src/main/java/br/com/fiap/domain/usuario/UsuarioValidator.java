package br.com.fiap.domain.usuario;

import br.com.fiap.domain.validation.Error;
import br.com.fiap.domain.validation.ValidationHandler;
import br.com.fiap.domain.validation.Validator;

import java.util.List;

public class UsuarioValidator extends Validator {

    private final Usuario usuario;

    public UsuarioValidator(final Usuario usuario, final ValidationHandler handler) {
        super(handler);
        this.usuario = usuario;
    }

    @Override
    public void validate() {
        validarCpf();
        validarEmail();
        validarNome();
        validarSenha();
        validarTipoUsuario();
    }

    private void validarCpf() {
        if(!usuario.getCpf().isValidCPF()){
            this.validationHandler().append(new Error("CPF inválido") );
        }
    }

    private void validarEmail() {
        if(usuario.getEmail() == null || !usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            this.validationHandler().append(new Error("Email inválido") );
        }
    }

    private void validarNome() {
        if(usuario.getNome() == null || usuario.getNome().isBlank()){
            this.validationHandler().append(new Error("Nome não pode ser vazio") );
        }
    }

    private void validarSenha() {
        if(usuario.getSenha() == null || usuario.getSenha().isBlank()){
            this.validationHandler().append(new Error("Senha não pode ser vazia") );
        }
    }

    private void validarTipoUsuario() {
        if(usuario.getTipoUsuario().isBlank()){
            this.validationHandler().append(new Error("Tipo de usuário não pode ser vazio") );
            return;
        }

        List<String> tiposValidos = List.of("MEDICO", "ENFERMEIRO", "PACIENTE");

        if(!tiposValidos.contains(usuario.getTipoUsuario())){
            this.validationHandler().append(new Error("Tipo de usuário inválido") );
        }
    }
}
