package br.com.fiap.techchallenge.application.consulta.ports.out;

import br.com.fiap.techchallenge.domain.usuario.Usuario;

import java.util.Optional;

public interface IUsuarioLeituraRepository {
    Optional<Usuario> findById(Long id);
}
