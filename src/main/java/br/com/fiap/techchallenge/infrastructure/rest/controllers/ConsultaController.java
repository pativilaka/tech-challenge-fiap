package br.com.fiap.techchallenge.infrastructure.rest.controllers;

import br.com.fiap.techchallenge.application.consulta.dto.AgendarConsultaRequestApp;
import br.com.fiap.techchallenge.application.consulta.dto.ConsultaResponseApp;
import br.com.fiap.techchallenge.application.consulta.dto.ListaConsultasResponseApp;
import br.com.fiap.techchallenge.application.consulta.dto.ReagendarConsultaRequestApp;
import br.com.fiap.techchallenge.application.consulta.ports.in.*;
import br.com.fiap.techchallenge.infrastructure.rest.presenters.ConsultaPresenterHttp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final IAgendarConsulta agendar;
    private final IReagendarConsulta reagendar;
    private final IIniciarConsulta iniciar;
    private final IConcluirConsulta concluir;
    private final ICancelarConsulta cancelar;
    private final IBuscarConsultaPorId buscarPorId;
    private final IListarConsultas listar;
    private final ConsultaPresenterHttp presenterHttp;

    @PostMapping
    public ResponseEntity<ConsultaResponseApp> agendar(@RequestBody AgendarConsultaRequestApp request) {
        agendar.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(presenterHttp.get());
    }

    @PutMapping("/{id}/reagendar")
    public ResponseEntity<ConsultaResponseApp> reagendar(@PathVariable Long id,
                                                         @RequestBody ReagendarConsultaRequestApp body) {
        ReagendarConsultaRequestApp req = new ReagendarConsultaRequestApp(id, body.novoInicio(), body.novoFim());
        reagendar.execute(req);
        return ResponseEntity.ok(presenterHttp.get());
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<ConsultaResponseApp> iniciar(@PathVariable Long id) {
        iniciar.execute(id);
        return ResponseEntity.ok((presenterHttp.get()));
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<ConsultaResponseApp> concluir(@PathVariable Long id) {
        concluir.execute(id);
        return ResponseEntity.ok(presenterHttp.get());
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ConsultaResponseApp> cancelar(@PathVariable Long id) {
        cancelar.execute(id);
        return ResponseEntity.ok(presenterHttp.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseApp> buscarPorId(@PathVariable Long id) {
        buscarPorId.execute(id);
        return ResponseEntity.ok(presenterHttp.get());
    }

    @GetMapping
    public ResponseEntity<ListaConsultasResponseApp> listar() {
        listar.execute();
        return ResponseEntity.ok(presenterHttp.getLista());
    }
}
