package br.com.fiap.techchallenge.infrastructure.persistence.jpa.repository;

import br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity.MedicoEntity;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity.PacienteEntity;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface  UsuarioJpaRepository  extends JpaRepository<UsuarioEntity, Long> {

    @Query("SELECT m FROM MedicoEntity m WHERE m.id = :usuarioId")
    Optional<MedicoEntity> getMedicoByUsuarioId(Long usuarioId);

    @Query("SELECT u FROM UsuarioEntity u WHERE u.id = :usuarioId" )
    Optional<PacienteEntity> getPacienteByUsuarioId(Long usuarioId);
}
