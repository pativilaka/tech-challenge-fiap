package br.com.fiap.msnotificacao.service;


import br.com.fiap.msnotificacao.cache.EventIdempotencyChecker;
import br.com.fiap.msnotificacao.dto.EventNotificationEmailDTO;
import br.com.fiap.msnotificacao.dto.Status;
import com.fasterxml.jackson.databind.util.IgnorePropertiesUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailParseException;

import java.time.Duration;

import static org.mockito.Mockito.*;

public class EmailConsumerServiceTest {
    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailConsumerService emailConsumerService;

    @Mock
    private EventIdempotencyChecker checker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveConsumirMensagemEEnviarEmail() {

        EventNotificationEmailDTO eventNotificationEmailDTO = new EventNotificationEmailDTO(
                1,
                "email@email.com",
                "Dr. Teste",
                "Paciente Teste",
                "2024-06-30 10:00",
                Status.AGENDADA
        );

        when(checker.validarERegistrarEvento(anyString(), any(Duration.class))).thenReturn(true);

        emailConsumerService.consumirNotificacaoConsulta(eventNotificationEmailDTO);

        verify(emailService, times(1))
                .enviarNotificacaoConsulta(eventNotificationEmailDTO);
    }

    @Test
    void deveTratarErroAoConsumirMensagem() {
        EventNotificationEmailDTO eventNotificationEmailDTO = new EventNotificationEmailDTO(
                1,
                "invalid-email",
                "Dr. Teste",
                "Paciente Teste",
                "2024-06-30 10:00",
                Status.AGENDADA
        );
        when(checker.validarERegistrarEvento(anyString(), any(Duration.class))).thenReturn(true);
        doThrow(new MailParseException("e-mail errado")).when(emailService).enviarNotificacaoConsulta(eventNotificationEmailDTO);


        var exception = Assertions.assertThrows(
                MailParseException.class,
                () -> emailConsumerService.consumirNotificacaoConsulta(eventNotificationEmailDTO));

        Assertions.assertEquals("e-mail errado", exception.getMessage());
        verify(emailService, times(1))
                .enviarNotificacaoConsulta(eventNotificationEmailDTO);
    }
}
