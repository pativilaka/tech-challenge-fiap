package br.com.fiap.techchallenge.infrastructure.kafka.adapter;

import br.com.fiap.techchallenge.application.consulta.ports.out.IConsultaProducer;
import br.com.fiap.techchallenge.application.consulta.ports.out.IUsuarioLeituraRepository;
import br.com.fiap.techchallenge.domain.comum.DomainException;
import br.com.fiap.techchallenge.domain.consulta.Consulta;
import br.com.fiap.techchallenge.domain.usuario.Medico;
import br.com.fiap.techchallenge.domain.usuario.Paciente;
import br.com.fiap.techchallenge.infrastructure.kafka.EventoNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ConsultaProducerKafkaAdapter implements IConsultaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final IUsuarioLeituraRepository usuarioLeituraRepository;

    public ConsultaProducerKafkaAdapter(KafkaTemplate<String, Object> kafkaTemplate,
                                        IUsuarioLeituraRepository usuarioLeituraRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.usuarioLeituraRepository = usuarioLeituraRepository;
    }

    @Override
    public void enviarEventoConsulta(Consulta consulta) {
        Paciente paciente = usuarioLeituraRepository.findPacienteById(consulta.getPacienteId()).orElseThrow(
                () -> new DomainException("Paciente não encontrado")
        );
        Medico medico = usuarioLeituraRepository.findMedicoById(consulta.getMedicoId()).orElseThrow(
                () -> new DomainException("Médico não encontrado")
        );

        EventoNotification evento = new EventoNotification(
                consulta.getId().intValue(),
                paciente.getEmail(),
                medico.getNome(),
                paciente.getNome(),
                consulta.getInicio().format(formatter),
                consulta.getStatus()
        );
        kafkaTemplate.send("notificacao_consulta", evento);
    }
}
