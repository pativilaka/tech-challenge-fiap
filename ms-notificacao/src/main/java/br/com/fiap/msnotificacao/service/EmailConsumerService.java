package br.com.fiap.msnotificacao.service;

import br.com.fiap.msnotificacao.cache.EventIdempotencyChecker;
import br.com.fiap.msnotificacao.dto.EventNotificationEmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class EmailConsumerService {

    private final Logger log = LoggerFactory.getLogger(EmailConsumerService.class);

    private final EmailService emailService;

    private final EventIdempotencyChecker checker;

    public EmailConsumerService(EmailService emailService, EventIdempotencyChecker checker) {
        this.emailService = emailService;
        this.checker = checker;
    }



    @KafkaListener(topics = "notificacao_consulta", concurrency = "3")
    public void consumirNotificacaoConsulta(EventNotificationEmailDTO mensagem) {
        try {
            if (validar(mensagem)) return;
            log.info("Notificação de consulta recebida: {}", mensagem);
            emailService.enviarNotificacaoConsulta(mensagem);
        } catch (Exception e) {
            log.error("Erro ao processar a notificação de consulta: {}", e.getMessage());
            throw e;
        }

    }

    private boolean validar(EventNotificationEmailDTO mensagem) {
        String idempotencyKey = "notificacao_consulta:" + mensagem.idConsulta() + ":" + mensagem.tipoEvento();
        if(!checker.validarERegistrarEvento(idempotencyKey, Duration.ofMinutes(5))) {
            log.warn("Evento de ID {} já processado. Ignorando mensagem duplicada.", mensagem.idConsulta());
            return true;
        }
        return false;
    }

}
