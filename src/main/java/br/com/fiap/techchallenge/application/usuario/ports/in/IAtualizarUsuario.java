package br.com.fiap.techchallenge.application.usuario.ports.in;

import br.com.fiap.techchallenge.application.usuario.dto.AtualizarUsuarioRequestApp;

public interface IAtualizarUsuario {

    void execute(Long id, AtualizarUsuarioRequestApp requestApp);

}
