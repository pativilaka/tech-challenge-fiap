package br.com.fiap.techchallenge.application.usuario.ports.in;

import br.com.fiap.techchallenge.application.usuario.dto.CriarUsuarioRequestApp;

public interface ICriarUsuario {

    void execute(CriarUsuarioRequestApp requestApp);

}
