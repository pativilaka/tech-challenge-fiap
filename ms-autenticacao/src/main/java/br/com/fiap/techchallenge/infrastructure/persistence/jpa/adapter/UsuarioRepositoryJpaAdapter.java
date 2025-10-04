package br.com.fiap.techchallenge.infrastructure.persistence.jpa.adapter;

import br.com.fiap.techchallenge.application.usuario.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.repository.UsuarioJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepositoryJpaAdapter implements IUsuarioRepository {

    private final UsuarioJpaRepository jpa;

    public UsuarioRepositoryJpaAdapter(UsuarioJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return jpa.findByEmail(email).map(UsuarioEntityMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpa.findById(id).map(UsuarioEntityMapper::toDomain);
    }

}
