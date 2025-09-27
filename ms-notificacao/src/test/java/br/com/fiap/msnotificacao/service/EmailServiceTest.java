package br.com.fiap.msnotificacao.service;

import br.com.fiap.msnotificacao.dto.EventNotificationEmailDTO;
import br.com.fiap.msnotificacao.dto.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @Mock
    private org.springframework.mail.javamail.JavaMailSender mailSender;

    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailService = new EmailService(mailSender);
    }

    @Test
    void deveEnviarEmail() {
        EventNotificationEmailDTO eventNotificationEmailDTO = new EventNotificationEmailDTO(
                1,
                "teste@exemplo.com",
                "Dr. Teste",
                "Paciente Teste",
                "2024-06-30 10:00",
                Status.CANCELADA
        );

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        emailService.enviarNotificacaoConsulta(eventNotificationEmailDTO);

        verify(mailSender, times(1))
                .send(any(SimpleMailMessage.class));
    }

    @Test
    void deveGerarExcecaoAoEnviarEmailComDadosInvalidos() {
        EventNotificationEmailDTO eventNotificationEmailDTO = new EventNotificationEmailDTO(
                1,
                "invalid-email",
                "Dr. Teste",
                "Paciente Teste",
                "2024-06-30 10:00",
                Status.CANCELADA
        );

        doThrow(new MailParseException("e-mail errado")).when(mailSender).send(any(SimpleMailMessage.class));


        var exception = assertThrows(MailParseException.class, () -> {
            emailService.enviarNotificacaoConsulta(eventNotificationEmailDTO);
        });

        assertEquals("e-mail errado", exception.getMessage());
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void deveEnviarEmailDeConfirmacao() {
        EventNotificationEmailDTO eventNotificationEmailDTO = new EventNotificationEmailDTO(
                1,
                "teste@exemplo.com",
                "Dr. Teste",
                "Paciente Teste",
                "2024-06-30 10:00",
                Status.AGENDADA
        );

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        emailService.enviarNotificacaoConsulta(eventNotificationEmailDTO);

        verify(mailSender, times(1))
                .send(any(SimpleMailMessage.class));
    }
}
