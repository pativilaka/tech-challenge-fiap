package br.com.fiap.techchallenge.domain.consulta;

import br.com.fiap.techchallenge.domain.comum.DomainException;
import br.com.fiap.techchallenge.domain.comum.DomainValidation;

import java.time.LocalDate;

public class Consulta {

    private final Long id;
    private Long medicoId;
    private Long pacienteId;
    private LocalDate inicio;
    private LocalDate fim;
    private StatusConsulta status;

    public Consulta(Long id, Long medicoId, Long pacienteId, LocalDate inicio,
                    LocalDate fim, StatusConsulta status) {
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

    public static Consulta agendar(Long id, Long medicoId, Long pacienteId, LocalDate inicio,
                                   LocalDate fim) {
        return new Consulta(id, medicoId, pacienteId, inicio, fim, StatusConsulta.AGENDADA);
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

    public void reagendar(LocalDate novoInicio, LocalDate novoFim) {
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

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public StatusConsulta getStatus() {
        return status;
    }
}
