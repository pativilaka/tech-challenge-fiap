package br.com.fiap.techchallenge.infrastructure.persistence.jpa.repository;

import br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaJpaRepository extends JpaRepository<ConsultaEntity, Long> {
}
