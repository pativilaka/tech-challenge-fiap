package br.com.fiap.techchallenge.application.usuario.ports.in;

import jakarta.security.auth.message.AuthException;

public interface IBuscarUsuario {

    void execute(String requestApp) throws AuthException;

}
