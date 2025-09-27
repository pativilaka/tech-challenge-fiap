package br.com.fiap.techchallenge.infrastructure.persistence.jpa.adapter;

import br.com.fiap.techchallenge.application.usuario.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.repository.UsuarioJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryJpaAdapter implements IUsuarioRepository {

    private final UsuarioJpaRepository jpa;

    public UsuarioRepositoryJpaAdapter(UsuarioJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Usuario save(Usuario usuario) {
        var saved = jpa.save(UsuarioEntityMapper.toEntity(usuario));
        return UsuarioEntityMapper.toDomain(saved);
    }

    @Override
    public Usuario update(Usuario usuario) {
        var saved = jpa.save(UsuarioEntityMapper.toEntity(usuario));
        return UsuarioEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpa.findById(id).map(UsuarioEntityMapper::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return jpa.findAll().stream().map(UsuarioEntityMapper::toDomain).toList();
    }

    @Override
    public boolean deleteById(Long id) {
        if (jpa.existsById(id)) {
            jpa.deleteById(id);
            return true;
        }
        return false;
    }
}
