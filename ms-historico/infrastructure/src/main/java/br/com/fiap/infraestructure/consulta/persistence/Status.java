package br.com.fiap.infraestructure.consulta.persistence;


public enum Status {
    AGENDADA,
    CANCELADA,
    ANDAMENTO,
    CONCLUIDA;

    public static Status from(String value) {
        for (Status tipo : values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + value);
    }
}
