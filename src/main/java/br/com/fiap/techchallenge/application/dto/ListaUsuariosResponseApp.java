package br.com.fiap.techchallenge.application.dto;

import java.util.List;

public record ListaUsuariosResponseApp(
        List<UsuarioResponseApp> itens
) {
}
