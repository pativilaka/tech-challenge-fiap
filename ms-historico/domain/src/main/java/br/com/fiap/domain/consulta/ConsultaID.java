package br.com.fiap.domain.consulta;

import br.com.fiap.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class ConsultaID extends Identifier {

    private final String value;

    private ConsultaID(final String value){
        Objects.nonNull(value);
        this.value = value;
    }

    public static ConsultaID unique() {
        return new ConsultaID(randomUUID().toString());
    }

    public static ConsultaID from(final String value) {
        return new ConsultaID(value);
    }

    public static ConsultaID from(final UUID value) {
        return new ConsultaID(value.toString());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsultaID that = (ConsultaID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
