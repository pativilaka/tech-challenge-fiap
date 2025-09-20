package br.com.fiap.techchallenge.application.comum;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message){
        super(message);
    }

}
