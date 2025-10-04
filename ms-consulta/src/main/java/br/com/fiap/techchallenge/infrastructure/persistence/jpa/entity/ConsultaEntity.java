package br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity;

import br.com.fiap.techchallenge.domain.consulta.StatusConsulta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "consultas")
public class ConsultaEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medico_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_consulta_medico"))
    private UsuarioEntity medico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_consulta_paciente"))
    private UsuarioEntity paciente;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime inicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDateTime fim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusConsulta status;

    public ConsultaEntity(Long id, UsuarioEntity medico, UsuarioEntity paciente,
                          LocalDateTime inicio, LocalDateTime fim, StatusConsulta status) {
        this.id = id;
        this.medico = medico;
        this.paciente = paciente;
        this.inicio = inicio;
        this.fim = fim;
        this.status = status;
    }

}
