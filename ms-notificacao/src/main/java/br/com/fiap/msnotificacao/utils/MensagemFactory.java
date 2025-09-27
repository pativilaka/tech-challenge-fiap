package br.com.fiap.msnotificacao.utils;

import br.com.fiap.msnotificacao.dto.Status;

public class MensagemFactory {

    public static String criarMensagem(String nomeDoutor, String nomePaciente, String dataHoraConsulta, Status tipoEvento) {
        return switch (tipoEvento) {
            case CANCELADA -> gerarMensagemCancelamento(nomeDoutor, nomePaciente);
            case AGENDADA -> gerarMensagemAgendamento(nomeDoutor, nomePaciente, dataHoraConsulta);
            default -> throw new IllegalArgumentException("Tipo de evento desconhecido: " + tipoEvento);
        };
    }

    private MensagemFactory() {}

    private static String gerarMensagemCancelamento(String nomeDoutor, String nomePaciente) {
        return String.format("Olá %s, sua consulta com o Dr. %s foi cancelada.", nomePaciente, nomeDoutor);
    }

    private static String gerarMensagemAgendamento(String nomeDoutor, String nomePaciente, String dataHoraConsulta) {
        return String.format("Olá %s, sua consulta com o Dr. %s está agendada para %s.", nomePaciente, nomeDoutor, dataHoraConsulta);
    }

}
