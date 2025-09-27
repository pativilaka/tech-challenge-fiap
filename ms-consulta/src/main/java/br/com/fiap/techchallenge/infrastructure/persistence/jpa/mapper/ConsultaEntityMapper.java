package br.com.fiap.techchallenge.infrastructure.persistence.jpa.mapper;

import br.com.fiap.techchallenge.domain.consulta.Consulta;
import br.com.fiap.techchallenge.domain.consulta.StatusConsulta;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity.ConsultaEntity;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity.UsuarioEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

public class ConsultaEntityMapper {

    private final EntityManager manager;

    public ConsultaEntityMapper(EntityManager manager) {
        this.manager = manager;
    }

    public ConsultaEntity toEntity(Consulta consulta) {
        UsuarioEntity medicoRef   = getUsuarioRef(consulta.getMedicoId());
        UsuarioEntity pacienteRef = getUsuarioRef(consulta.getPacienteId());

        return new ConsultaEntity(
                consulta.getId(),
                medicoRef,
                pacienteRef,
                consulta.getInicio(),
                consulta.getFim(),
                consulta.getStatus()
        );
    }

    public Consulta toDomain(ConsultaEntity entity) {
        return Consulta.rebuild(
                entity.getId(),
                entity.getMedico().getId(),
                entity.getPaciente().getId(),
                entity.getInicio(),
                entity.getFim(),
                entity.getStatus()
        );
    }


    private UsuarioEntity getUsuarioRef(Long id) {
        try {
            return manager.getReference(UsuarioEntity.class, id);
        } catch (EntityNotFoundException ex) {
            throw ex;
        }
    }
}
