package br.com.fiap.infraestructure.consulta.persistence;

import br.com.fiap.domain.consulta.Consulta;
import br.com.fiap.infraestructure.usuario.persistence.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaJpaEntity, String > {

    @Query("SELECT c FROM ConsultaJpaEntity c WHERE c.paciente = :usuarioId OR c.medico = :usuarioId")
    List<ConsultaJpaEntity> findAllByUsuarioId(@Param("usuarioId") String usuarioId);


}
