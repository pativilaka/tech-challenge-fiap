package br.com.fiap.techchallenge.application.dto;

public record EnderecoApp(
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String uf,
        String cep,
        String complemento
) {}
