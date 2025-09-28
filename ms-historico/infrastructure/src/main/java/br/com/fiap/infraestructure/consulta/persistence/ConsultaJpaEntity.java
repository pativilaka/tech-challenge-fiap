package br.com.fiap.infraestructure.consulta.persistence;

import br.com.fiap.domain.consulta.Consulta;
import br.com.fiap.domain.consulta.ConsultaID;
import br.com.fiap.domain.usuario.UsuarioID;
import br.com.fiap.infraestructure.usuario.persistence.UsuarioJpaEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity
public class ConsultaJpaEntity {

    public ConsultaJpaEntity() {}

    private ConsultaJpaEntity(
            final Integer id,
            final Integer pacienteId,
            final Integer medicoId,
            final LocalDateTime dataInicio,
            final LocalDateTime dataFim,
            final Status status
    ) {
        this.id = id;
        this.paciente = pacienteId;
        this.medico = medicoId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
    }

    @Id
    private Integer id;

    @Column(name = "paciente_id")
    private Integer paciente;

    @Column(name = "medico_id")
    private Integer medico;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;


    @Enumerated(EnumType.STRING)
    private Status status;

    public static ConsultaJpaEntity from(final Integer id,
                                         final Integer  pacienteId,
                                         final Integer medicoId,
                                         final LocalDateTime dataInicio,
                                         final LocalDateTime dataFim,
                                         final Status status) {
        return new ConsultaJpaEntity(
                id,
                pacienteId,
                medicoId,
                dataInicio,
                dataFim,
                status
        );
    }

    public Consulta toAggregate() {
        return Consulta.with(
                ConsultaID.from(this.id),
                UsuarioID.from(this.paciente),
                UsuarioID.from(this.medico),
                this.dataInicio,
                this.status.name()
        );
    }


}
