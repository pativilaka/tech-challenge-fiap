package br.com.fiap.techchallenge.infrastructure.rest.controllers;

import br.com.fiap.techchallenge.application.usuario.dto.AuthenticationResponse;
import br.com.fiap.techchallenge.application.usuario.dto.LoginRequestApp;
import br.com.fiap.techchallenge.application.usuario.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.application.usuario.ports.in.*;
import br.com.fiap.techchallenge.infrastructure.rest.presenters.AutenticacaoPresenterHttp;
import br.com.fiap.techchallenge.infrastructure.rest.presenters.UsuarioPresenterHttp;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    private final IAutenticarUsuario autenticarUsuario;
    private final AutenticacaoPresenterHttp autenticarPresenterHttp;
    private final IBuscarUsuario buscarUsuario;
    private final UsuarioPresenterHttp usuarioPresenterHttp;

    @GetMapping("/resgatar/usuario")
    public ResponseEntity<UsuarioResponseApp> execute(HttpServletRequest request) throws AuthException {
        String token = request.getHeader("Authorization").substring(7);
        buscarUsuario.execute(token);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioPresenterHttp.get());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequestApp request) {
        autenticarUsuario.execute(request);
        return ResponseEntity.status(HttpStatus.OK).body(autenticarPresenterHttp.get());
    }

}
