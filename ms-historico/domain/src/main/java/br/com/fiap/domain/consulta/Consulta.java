package br.com.fiap.domain.consulta;

import br.com.fiap.domain.AggregateRoot;
import br.com.fiap.domain.exceptions.NotificationException;
import br.com.fiap.domain.usuario.UsuarioID;
import br.com.fiap.domain.validation.ValidationHandler;
import br.com.fiap.domain.validation.handler.Notification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Consulta extends AggregateRoot<ConsultaID> implements Cloneable {


    private final UsuarioID medicoId;

    private final UsuarioID pacienteId;

    private final LocalDateTime dataHoraConsulta;

    private final String status;

    protected Consulta(final ConsultaID consultaID,
                       final UsuarioID medicoId,
                       final UsuarioID pacienteId,
                       final LocalDateTime dataHoraConsulta,
                       final String status) {
        super(consultaID);
        this.medicoId = medicoId;
        this.pacienteId = pacienteId;
        this.dataHoraConsulta = dataHoraConsulta;
        this.status = status;
        selfValidate();
    }

    public static Consulta newConsulta(final ConsultaID consultaID,
                                final UsuarioID medicoId,
                                final UsuarioID pacienteId,
                                final LocalDateTime dataHoraConsulta,
                                final String status) {
        return new Consulta(
                consultaID,
                medicoId,
                pacienteId,
                dataHoraConsulta,
                status
        );
    }

    public static Consulta with(final ConsultaID anId,
                                final UsuarioID medicoId,
                                final UsuarioID pacienteId,
                                final LocalDateTime dataHoraConsulta,
                                final String status) {
        return new Consulta(
                anId,
                medicoId,
                pacienteId,
                dataHoraConsulta,
                status
        );
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ConsultaValidator(this, handler).validate();
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Falha ao criar objeto usuário", notification);
        }
    }

    @Override
    public Consulta clone() {
        try {
            return (Consulta) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public UsuarioID getMedicoId() {
        return medicoId;
    }

    public UsuarioID getPacienteId() {
        return pacienteId;
    }

    public LocalDateTime getdataHoraConsulta() {
        return dataHoraConsulta;
    }

    public String getStatus() {
        return status;
    }
}
