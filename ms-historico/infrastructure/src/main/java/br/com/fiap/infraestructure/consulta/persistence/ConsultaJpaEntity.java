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
            final String id,
            final String pacienteId,
            final String medicoId,
            final LocalDateTime dataHora,
            final Status status
    ) {
        this.id = id;
        this.paciente = pacienteId;
        this.medico = medicoId;
        this.dataHora = dataHora;
        this.status = status;
    }

    @Id
    private String id;

    @Column(name = "paciente_id")
    private String paciente;

    @Column(name = "medico_id")
    private String medico;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private Status status;

    public static ConsultaJpaEntity from(final String id,
                                         final String  pacienteId,
                                         final String medicoId,
                                         final LocalDateTime dataHora,
                                         final Status status) {
        return new ConsultaJpaEntity(
                id,
                pacienteId,
                medicoId,
                dataHora,
                status
        );
    }

    public Consulta toAggregate() {
        return Consulta.with(
                ConsultaID.from(this.id),
                UsuarioID.from(this.paciente),
                UsuarioID.from(this.medico),
                this.dataHora,
                this.status.name()
        );
    }


}
