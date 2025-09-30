package br.com.fiap.techchallenge.domain.comum;

public class DomainException extends RuntimeException{
    public DomainException(String message){
        super(message);
    }
}
