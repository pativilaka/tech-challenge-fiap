package br.com.fiap.techchallenge.application.usuario.ports.in;

import br.com.fiap.techchallenge.application.usuario.dto.LoginRequestApp;

public interface IAutenticarUsuario {

    void execute(LoginRequestApp requestApp);

}
