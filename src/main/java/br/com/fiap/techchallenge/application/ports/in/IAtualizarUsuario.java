package br.com.fiap.techchallenge.application.ports.in;

import br.com.fiap.techchallenge.application.dto.AtualizarUsuarioRequestApp;

public interface IAtualizarUsuario {

    void execute(AtualizarUsuarioRequestApp requestApp);

}
