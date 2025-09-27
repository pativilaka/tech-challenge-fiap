package br.com.fiap.domain.consulta;

import br.com.fiap.domain.validation.Error;
import br.com.fiap.domain.validation.ValidationHandler;
import br.com.fiap.domain.validation.Validator;

public class ConsultaValidator extends Validator {

    private final Consulta consulta;

    public ConsultaValidator(final Consulta consulta, final ValidationHandler handler) {
        super(handler);
        this.consulta = consulta;
    }

    @Override
    public void validate() {
        validateMedicoId();
        validateStatus();
    }

    private void validateMedicoId() {
        if (this.consulta.getMedicoId() == null) {
            this.validationHandler().append(new Error("medicoId não pode ser nulo"));
        }
    }

    private void validateStatus() {
        if (this.consulta.getStatus() == null || this.consulta.getStatus().isBlank()) {
            this.validationHandler().append(new Error("status não pode ser vazio"));
        }
        else {
            if (!this.consulta.getStatus().matches("^(AGENDADA|CANCELADA|REMARCADA|CONCLUIDA)$")) {
                this.validationHandler().append(new Error("status inválido"));
            }
        }

    }
}
