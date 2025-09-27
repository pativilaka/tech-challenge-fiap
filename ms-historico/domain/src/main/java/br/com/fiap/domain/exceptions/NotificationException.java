package br.com.fiap.domain.exceptions;

import br.com.fiap.domain.validation.handler.Notification;

public class NotificationException extends DomainException {
    public NotificationException(String message, Notification notification) {
        super(message, notification.getErrors());
    }
}
