package br.com.fiap.techchallenge.domain.comum;

public final class DomainValidation {

    private DomainValidation() {}

    public static void notBlank(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new DomainException("Campo '" + campo + "' é obrigatório.");
        }
    }

    public static void notNull(Object valor, String campo) {
        if (valor == null) {
            throw new DomainException("Campo '" + campo + "' não pode ser nulo.");
        }
    }

    public static void minLength(String valor, int min, String campo) {
        if (valor != null && valor.trim().length() < min) {
            throw new DomainException("Campo '" + campo + "' deve ter pelo menos " + min + " caracteres.");
        }
    }

    public static void email(String valor, String campo) {
        if (valor == null || !valor.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new DomainException("Campo '" + campo + "' não é um e-mail válido.");
        }
    }

    public static void cpf(String valor, String campo) {
        if (valor == null || !valor.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
            throw new DomainException("Campo '" + campo + "' não é um CPF válido.");
        }
    }

    public static void telefone(String valor, String campo) {
        String regex = "^\\d{2}\\s(?:\\d{4}-\\d{4}|9\\d{4}-\\d{4})$";
        if (valor == null || !valor.matches(regex)) {
            throw new DomainException("Campo '" + campo + "' não é um telefone válido.");
        }
    }
}
