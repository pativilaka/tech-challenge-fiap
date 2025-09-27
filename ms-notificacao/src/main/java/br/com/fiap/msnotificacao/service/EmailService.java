package br.com.fiap.msnotificacao.service;

import br.com.fiap.msnotificacao.dto.EventNotificationEmailDTO;
import br.com.fiap.msnotificacao.utils.MensagemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final org.springframework.mail.javamail.JavaMailSender mailSender;

    public EmailService(org.springframework.mail.javamail.JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarNotificacaoConsulta(EventNotificationEmailDTO evento) {
        var mensagem = gerarMensagem(evento);
        try {
            mailSender.send(mensagem);
            log.info("Email de notificação enviado para: {}", evento.email());
        } catch (MailException e) {
            log.error("Erro ao enviar email para {}: {}", evento.email(), e.getMessage());
            throw e;
        }

    }

    private SimpleMailMessage gerarMensagem(EventNotificationEmailDTO evento) {
        var mensagem = new SimpleMailMessage();
        mensagem.setTo(evento.email());
        mensagem.setSubject("Notificação de Consulta");
        mensagem.setText(MensagemFactory.criarMensagem(
                evento.nomeDoutor(),
                evento.nomePaciente(),
                evento.dataHoraConsulta(),
                evento.tipoEvento()));
        return mensagem;
    }
}
