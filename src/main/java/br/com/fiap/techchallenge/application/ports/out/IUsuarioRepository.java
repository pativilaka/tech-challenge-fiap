package br.com.fiap.techchallenge.application.ports.out;

import br.com.fiap.techchallenge.domain.usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository {
    Usuario save(Usuario usuario);
    Usuario update(Usuario usuario);
    Optional<Usuario> findById(Long id);
    List<Usuario> findAll();
    boolean deleteById(Long id);
}
