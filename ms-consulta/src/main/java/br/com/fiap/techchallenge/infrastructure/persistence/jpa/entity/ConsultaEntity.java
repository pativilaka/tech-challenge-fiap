package br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity;

import br.com.fiap.techchallenge.domain.consulta.StatusConsulta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "consultas")
public class ConsultaEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consulta_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medico_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_consulta_medico"))
    private UsuarioEntity medico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_consulta_paciente"))
    private UsuarioEntity paciente;

    @Column(name = "inicio", nullable = false)
    private LocalDate inicio;

    @Column(name = "fim", nullable = false)
    private LocalDate fim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusConsulta status;

    public ConsultaEntity(Long id, UsuarioEntity medico, UsuarioEntity paciente,
                          LocalDate inicio, LocalDate fim, StatusConsulta status) {
        this.id = id;
        this.medico = medico;
        this.paciente = paciente;
        this.inicio = inicio;
        this.fim = fim;
        this.status = status;
    }

}
