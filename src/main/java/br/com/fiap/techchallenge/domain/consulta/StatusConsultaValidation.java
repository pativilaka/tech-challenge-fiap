package br.com.fiap.techchallenge.domain.consulta;

import br.com.fiap.techchallenge.domain.comum.DomainException;

public final class StatusConsultaValidation {

    private StatusConsultaValidation(){}

    static void validarTransicao(StatusConsulta atual, StatusConsulta novo){

        switch (atual){
            case AGENDADA -> {
                if (!(novo == StatusConsulta.EM_ANDAMENTO || novo == StatusConsulta.CANCELADA)){
                    throw new DomainException("Status AGENDADA só pode avançar para status EM_ANDAMENTO" +
                            " ou status CANCELADA.");
                }
            }
            case EM_ANDAMENTO -> {
                if (!(novo == StatusConsulta.CONCLUIDA || novo == StatusConsulta.CANCELADA)){
                    throw new DomainException("Status EM_ANDAMENTO só pode avançar para status CONCLUIDA" +
                            " ou status CANCELADA.");
                }
            }
            case CONCLUIDA, CANCELADA -> {
                throw new DomainException("Status CONCLUIDA e CANCELADA são status finais.");
            }
            default -> throw new DomainException("Status inválido.");
        }
    }

}
