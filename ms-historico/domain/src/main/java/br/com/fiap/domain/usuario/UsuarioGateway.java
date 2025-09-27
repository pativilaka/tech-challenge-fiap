package br.com.fiap.domain.usuario;

import java.util.Optional;

public interface UsuarioGateway {

    Optional<Usuario> findById(UsuarioID usuarioID);

}
