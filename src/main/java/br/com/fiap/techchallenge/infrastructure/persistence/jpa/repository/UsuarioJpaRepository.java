package br.com.fiap.techchallenge.infrastructure.persistence.jpa.repository;

import br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UsuarioJpaRepository  extends JpaRepository<UsuarioEntity, Long> {
}
