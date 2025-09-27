package br.com.fiap.domain.exceptions;

import br.com.fiap.domain.AggregateRoot;
import br.com.fiap.domain.Identifier;
import br.com.fiap.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {
    protected NotFoundException(final String message, final List<Error> errors) {
        super(message, errors);
    }

    public static NotFoundException with(final Class<? extends AggregateRoot<?>> anAggregate,
                                         final Identifier id
    ){
        final var anError = "%s não encontrado com o ID: %s".formatted(
                anAggregate.getSimpleName(),
                id.getValue()
        );

        return new NotFoundException(anError, Collections.emptyList());
    }
}
