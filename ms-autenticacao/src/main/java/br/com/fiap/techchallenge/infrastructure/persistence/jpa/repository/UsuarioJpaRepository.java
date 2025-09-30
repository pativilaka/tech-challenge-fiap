package br.com.fiap.techchallenge.infrastructure.persistence.jpa.repository;

import br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  UsuarioJpaRepository  extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByEmail(String email);
}
