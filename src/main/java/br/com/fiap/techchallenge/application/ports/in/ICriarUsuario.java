package br.com.fiap.techchallenge.application.ports.in;

import br.com.fiap.techchallenge.application.dto.CriarUsuarioRequestApp;

public interface ICriarUsuario {

    void execute(CriarUsuarioRequestApp requestApp);

}
