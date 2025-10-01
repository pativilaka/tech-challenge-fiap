package br.com.fiap.application.usuario.retrieve.get;

import br.com.fiap.domain.exceptions.NotFoundException;
import br.com.fiap.domain.usuario.Usuario;
import br.com.fiap.domain.usuario.UsuarioGateway;
import br.com.fiap.domain.usuario.UsuarioID;

import java.util.function.Supplier;

public class DefaultGetUsuarioByIdUseCase extends GetUsuarioByIdUseCase {

    private final UsuarioGateway usuarioGateway;

    public DefaultGetUsuarioByIdUseCase(final UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public UsuarioOutput execute(Integer anIn) {
        final var usuarioId = UsuarioID.from(anIn);
        final var usuario = this.usuarioGateway.findById(usuarioId)
                .orElseThrow(notFound(usuarioId));
        return UsuarioOutput.from(usuario);
    }

    private static Supplier<NotFoundException> notFound(UsuarioID anId) {
        return () -> NotFoundException.with(Usuario.class, anId);
    }
}
