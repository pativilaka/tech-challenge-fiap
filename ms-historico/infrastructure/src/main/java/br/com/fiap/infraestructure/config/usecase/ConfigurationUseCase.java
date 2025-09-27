package br.com.fiap.infraestructure.config.usecase;

import br.com.fiap.application.consulta.retrive.list.usuario.DefaultGetConsultaByUsuarioIdUseCase;
import br.com.fiap.application.consulta.retrive.list.usuario.GetConsultaByUsuarioIdUseCase;
import br.com.fiap.application.usuario.retrieve.get.DefaultGetUsuarioByIdUseCase;
import br.com.fiap.application.usuario.retrieve.get.GetUsuarioByIdUseCase;
import br.com.fiap.domain.consulta.ConsultaGateway;
import br.com.fiap.domain.usuario.UsuarioGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationUseCase {

    private final ConsultaGateway consultaGateway;

    private final UsuarioGateway usuarioGateway;

    public ConfigurationUseCase(final ConsultaGateway consultaGateway,
                                final UsuarioGateway usuarioGateway) {
        this.consultaGateway = consultaGateway;
        this.usuarioGateway = usuarioGateway;
    }

    @Bean
    public GetConsultaByUsuarioIdUseCase getConsultaByUsuarioIdUseCase() {
        return new DefaultGetConsultaByUsuarioIdUseCase(this.consultaGateway);
    }

    @Bean
    public GetUsuarioByIdUseCase getUsuarioByIdUseCase() {
        return new DefaultGetUsuarioByIdUseCase(this.usuarioGateway);
    }
}
