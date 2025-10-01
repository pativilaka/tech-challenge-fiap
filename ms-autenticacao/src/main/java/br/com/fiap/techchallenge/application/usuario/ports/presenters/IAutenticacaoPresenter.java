package br.com.fiap.techchallenge.application.usuario.ports.presenters;

import br.com.fiap.techchallenge.application.usuario.dto.AuthenticationResponse;

public interface IAutenticacaoPresenter {

    AuthenticationResponse present(AuthenticationResponse result);
}
