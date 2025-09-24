package br.com.fiap.techchallenge.infrastructure.persistence.jpa.adapter;

import br.com.fiap.techchallenge.application.consulta.ports.out.IUsuarioLeituraRepository;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.repository.UsuarioJpaRepository;

import java.util.Optional;

public class UsuarioLeituraRepositoryJpaAdapter implements IUsuarioLeituraRepository {

    private final UsuarioJpaRepository jpa;

    public UsuarioLeituraRepositoryJpaAdapter(UsuarioJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpa.findById(id).map(UsuarioEntityMapper::toDomain);
    }
}
