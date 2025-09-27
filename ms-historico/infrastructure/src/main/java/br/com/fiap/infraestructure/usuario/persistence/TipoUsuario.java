package br.com.fiap.infraestructure.usuario.persistence;

public enum TipoUsuario {
    MEDICO,
    PACIENTE,
    ENFERMEIRO;

    public static TipoUsuario from(String value) {
        for (TipoUsuario tipo : values()) {
            if (tipo.name().equalsIgnoreCase(value)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de usuário inválido: " + value);
    }
}
