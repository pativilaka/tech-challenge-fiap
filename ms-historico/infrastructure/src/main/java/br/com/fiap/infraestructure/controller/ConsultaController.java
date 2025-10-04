package br.com.fiap.infraestructure.controller;

import br.com.fiap.application.consulta.retrive.list.usuario.GetConsultaByUsuarioIdUseCase;
import br.com.fiap.application.consulta.retrive.list.usuario.ListConsultaOutput;
import br.com.fiap.application.usuario.retrieve.get.GetUsuarioByIdUseCase;
import br.com.fiap.application.usuario.retrieve.get.UsuarioOutput;
import br.com.fiap.infraestructure.consulta.model.ConsultaResponse;
import br.com.fiap.infraestructure.consulta.presenter.ConsultaGraphPresenter;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ConsultaController {

    private final GetConsultaByUsuarioIdUseCase consultaUseCase;
    private final GetUsuarioByIdUseCase usuarioUseCase;

    public ConsultaController(GetConsultaByUsuarioIdUseCase consultaUseCase,
                              GetUsuarioByIdUseCase usuarioUseCase) {
        this.consultaUseCase = consultaUseCase;
        this.usuarioUseCase = usuarioUseCase;
    }


    @QueryMapping
    @PreAuthorize("hasRole('PACIENTE') or hasRole('MEDICO')")
    public List<ConsultaResponse> consultasPorUsuario(BearerTokenAuthentication authentication) {
        Integer usuarioId = (Integer) authentication.getTokenAttributes().get("id");

        List<ConsultaResponse> response = new ArrayList<>();
        List<ListConsultaOutput> consultaResponses = consultaUseCase.execute(usuarioId);

        for (ListConsultaOutput consultaOutput : consultaResponses) {
            UsuarioOutput paciente = usuarioUseCase.execute(consultaOutput.pacienteId().getValue());
            UsuarioOutput medico = usuarioUseCase.execute(consultaOutput.medicoId().getValue());
            response.add(ConsultaGraphPresenter.consultaResponse(consultaOutput, paciente, medico));
        }

        return response;
    }
}