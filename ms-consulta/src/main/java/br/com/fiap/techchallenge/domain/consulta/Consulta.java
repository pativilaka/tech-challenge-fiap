package br.com.fiap.techchallenge.domain.consulta;

import br.com.fiap.techchallenge.domain.comum.DomainException;
import br.com.fiap.techchallenge.domain.comum.DomainValidation;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Consulta {

    private final Long id;
    private Long medicoId;
    private Long pacienteId;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private StatusConsulta status;

    private Consulta(Long id, Long medicoId, Long pacienteId, LocalDateTime inicio,
                     LocalDateTime fim, StatusConsulta status) {
        this.id = id;
        this.medicoId = medicoId;
        this.pacienteId = pacienteId;
        this.inicio = inicio;
        this.fim = fim;
        this.status = status;
        validar();
    }

    private void validar(){

        DomainValidation.notNull(medicoId, "medicoId");
        DomainValidation.notNull(pacienteId, "pacienteId");
        DomainValidation.notNull(inicio, "inicio");
        DomainValidation.notNull(fim, "fim");
        DomainValidation.notNull(status, "status");

        if (inicio.isAfter(fim)){
            throw new DomainException("Data de início não pode ser depois da data de fim");
        }

        DomainValidation.registros(medicoId, pacienteId);
    }

    public static Consulta agendar(Long id, Long medicoId, Long pacienteId, LocalDateTime inicio,
                                   LocalDateTime fim) {
        return new Consulta(id, medicoId, pacienteId, inicio, fim, StatusConsulta.AGENDADA);
    }

    /**
     * Fábrica de HIDRATAÇÃO (reconstrução a partir do persistido).
     * Usa o status já salvo no banco, sem chamar iniciar()/concluir()/cancelar().
     */
    public static Consulta rebuild(Long id, Long medicoId, Long pacienteId, LocalDateTime inicio,
                                   LocalDateTime fim, StatusConsulta status) {
        return new Consulta(id, medicoId, pacienteId, inicio, fim, status);
    }

    public void iniciar() {
        StatusConsultaValidation.validarTransicao(this.status, StatusConsulta.EM_ANDAMENTO);
        this.status = StatusConsulta.EM_ANDAMENTO;
    }

    public void concluir() {
        StatusConsultaValidation.validarTransicao(this.status, StatusConsulta.CONCLUIDA);
        this.status = StatusConsulta.CONCLUIDA;
    }

    public void cancelar() {
        StatusConsultaValidation.validarTransicao(this.status, StatusConsulta.CANCELADA);
        this.status = StatusConsulta.CANCELADA;
    }

    public void reagendar(LocalDateTime novoInicio, LocalDateTime novoFim) {
        if (this.status == StatusConsulta.CONCLUIDA || this.status == StatusConsulta.CANCELADA) {
            throw new DomainException("Não é possível reagendar uma consulta finalizada.");
        }

        DomainValidation.notNull(novoInicio, "nova data");
        DomainValidation.notNull(novoFim, "data final");
        this.inicio = novoInicio;
        this.fim =  novoFim;
        validar();
    }

    public void trocarMedico(Long novoMedicoId) {
        if (this.status == StatusConsulta.CONCLUIDA || this.status == StatusConsulta.CANCELADA) {
            throw new DomainException("Não é possível trocar médico após finalização.");
        }

        DomainValidation.notNull(novoMedicoId, "novo médico");
        this.medicoId = novoMedicoId;
        validar();
    }

    public void trocarPaciente(Long novoPacienteId) {
        if (this.status == StatusConsulta.CONCLUIDA || this.status == StatusConsulta.CANCELADA) {
            throw new DomainException("Não é possível trocar paciente após finalização.");
        }

        DomainValidation.notNull(novoPacienteId, "novo paciente");
        this.pacienteId = novoPacienteId;
        validar();
    }

    public Long getId() {
        return id;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public StatusConsulta getStatus() {
        return status;
    }
}
