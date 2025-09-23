package br.com.fiap.techchallenge.application.usuario.dto;

import java.util.List;

public record ListaUsuariosResponseApp(
        List<UsuarioResponseApp> itens
) {
}
