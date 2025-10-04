package br.com.fiap.techchallenge.infrastructure.rest.controllers.advice;

import br.com.fiap.techchallenge.application.comum.dto.Erro;
import br.com.fiap.techchallenge.domain.comum.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(
        basePackages = "br.com.fiap.techchallenge.infrastructure.rest.controllers")
public class ExceptionHandlerAdvice {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Erro> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Erro(ex.getMessage(), "Erro de domínio"));
    }
}
