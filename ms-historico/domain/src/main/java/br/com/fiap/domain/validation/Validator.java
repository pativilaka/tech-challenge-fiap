package br.com.fiap.domain.validation;


public abstract class Validator {
    private final ValidationHandler handler;

    protected Validator(final ValidationHandler validationHandler) {
        this.handler = validationHandler;
    }

    public abstract void validate();

    protected ValidationHandler validationHandler() {
        return handler;
    }
}
