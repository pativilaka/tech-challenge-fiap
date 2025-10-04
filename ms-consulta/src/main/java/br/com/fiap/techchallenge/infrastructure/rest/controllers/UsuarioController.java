package br.com.fiap.techchallenge.infrastructure.rest.controllers;

import br.com.fiap.techchallenge.application.usuario.dto.AtualizarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.usuario.dto.CriarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.usuario.dto.ListaUsuariosResponseApp;
import br.com.fiap.techchallenge.application.usuario.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.application.usuario.ports.in.*;
import br.com.fiap.techchallenge.infrastructure.rest.presenters.UsuarioPresenterHttp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final ICriarUsuario criarUsuario;
    private final IAtualizarUsuario atualizarUsuario;
    private final IBuscarUsuarioPorId buscarUsuarioPorId;
    private final IListarUsuarios listarUsuarios;
    private final IDeletarUsuario deletarUsuario;
    private final UsuarioPresenterHttp presenterHttp;

    @PostMapping
    public ResponseEntity<UsuarioResponseApp> criar(@RequestBody CriarUsuarioRequestApp request) {
        criarUsuario.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(presenterHttp.get());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ENFERMEIRO')")
    public ResponseEntity<UsuarioResponseApp> atualizar(@PathVariable Long id,
                                          @RequestBody AtualizarUsuarioRequestApp request) {

        atualizarUsuario.execute(id, request);
        return ResponseEntity.ok(presenterHttp.get());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ENFERMEIRO')")
    public ResponseEntity<UsuarioResponseApp> buscarPorId(@PathVariable Long id) {
        buscarUsuarioPorId.execute(id);
        return ResponseEntity.ok(presenterHttp.get());
    }

    @GetMapping
    @PreAuthorize("hasRole('ENFERMEIRO')")
    public ResponseEntity<ListaUsuariosResponseApp> listarTodos() {
        listarUsuarios.execute();
        return ResponseEntity.ok(presenterHttp.getLista());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ENFERMEIRO')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        deletarUsuario.execute(id);
        return ResponseEntity.noContent().build();
    }
}
