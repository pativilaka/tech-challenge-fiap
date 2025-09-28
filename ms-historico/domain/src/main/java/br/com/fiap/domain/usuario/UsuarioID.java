package br.com.fiap.domain.usuario;

import br.com.fiap.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class UsuarioID extends Identifier {

    private final Integer value;

    private UsuarioID(final Integer value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static UsuarioID from(final Integer value) {
        return new UsuarioID(value);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioID that = (UsuarioID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

