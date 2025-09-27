package br.com.fiap.domain.usuario;

import br.com.fiap.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class UsuarioID extends Identifier {

    private final String value;

    private UsuarioID(final String value){
        Objects.nonNull(value);
        this.value = value;
    }

    public static UsuarioID unique() {
        return new UsuarioID(randomUUID().toString());
    }

    public static UsuarioID from(final String value) {
        return new UsuarioID(value);
    }

    public static UsuarioID from(final UUID value) {
        return new UsuarioID(value.toString());
    }

    @Override
    public String getValue() {
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
}
