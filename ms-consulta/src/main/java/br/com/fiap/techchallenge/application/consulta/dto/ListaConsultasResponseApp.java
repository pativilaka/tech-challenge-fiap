package br.com.fiap.techchallenge.application.consulta.dto;

import java.util.List;

public record ListaConsultasResponseApp(
        List<ConsultaResponseApp> consultas
) {
}
