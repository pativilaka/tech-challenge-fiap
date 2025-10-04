package br.com.fiap.techchallenge.application.usuario.ports.in;

import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IBuscarUsuario extends UserDetailsService {

    void execute(String requestApp) throws AuthException;

}
