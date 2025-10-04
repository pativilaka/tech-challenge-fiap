package br.com.fiap.techchallenge.application.usuario.ports.out;

import br.com.fiap.techchallenge.domain.usuario.Usuario;

import java.util.Optional;

public interface IUsuarioRepository {
    Optional<Usuario> findByEmail(String username);

    Optional<Usuario> findById(Long id);
}
