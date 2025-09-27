package br.com.fiap.techchallenge.infrastructure.config;

import br.com.fiap.techchallenge.application.consulta.ports.in.*;
import br.com.fiap.techchallenge.application.consulta.ports.out.IConsultaRepository;
import br.com.fiap.techchallenge.application.consulta.ports.out.IUsuarioLeituraRepository;
import br.com.fiap.techchallenge.application.consulta.ports.presenters.IConsultaPresenter;
import br.com.fiap.techchallenge.application.consulta.usecases.*;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.adapter.ConsultaRepositoryJpaAdapter;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.adapter.UsuarioLeituraRepositoryJpaAdapter;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.mapper.ConsultaEntityMapper;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.repository.ConsultaJpaRepository;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.repository.UsuarioJpaRepository;
import br.com.fiap.techchallenge.infrastructure.rest.presenters.ConsultaPresenterHttp;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ConsultaEntityMapper consultaEntityMapper(EntityManager manager) {
        return new ConsultaEntityMapper(manager);
    }

    @Bean
    public ConsultaRepositoryJpaAdapter consultaRepositoryJpaAdapter(
            ConsultaJpaRepository jpa,
            ConsultaEntityMapper mapper
    ) {
        return new ConsultaRepositoryJpaAdapter(jpa, mapper);
    }

    @Bean
    public IUsuarioLeituraRepository usuarioLeituraRepository(UsuarioJpaRepository springRepo) {
        return new UsuarioLeituraRepositoryJpaAdapter(springRepo);
    }

    @Bean
    public IAgendarConsulta agendarConsulta(IConsultaRepository consultaRepo,
                                            IConsultaPresenter presenter,
                                            IUsuarioLeituraRepository usuarioLeituraRepo) {
        return new AgendarConsultaUseCase(consultaRepo, presenter, usuarioLeituraRepo);
    }

    @Bean
    public IReagendarConsulta reagendarConsulta(IConsultaRepository consultaRepo,
                                                IConsultaPresenter presenter) {
        return new ReagendarConsultaUseCase(consultaRepo, presenter);
    }

    @Bean
    public IIniciarConsulta iniciarConsulta(IConsultaRepository consultaRepo,
                                            IConsultaPresenter presenter) {
        return new IniciarConsultaUseCase(consultaRepo, presenter);
    }

    @Bean
    public IConcluirConsulta concluirConsulta(IConsultaRepository consultaRepo,
                                              IConsultaPresenter presenter) {
        return new ConcluirConsultaUseCase(consultaRepo, presenter);
    }

    @Bean
    public ICancelarConsulta cancelarConsulta(IConsultaRepository consultaRepo,
                                              IConsultaPresenter presenter) {
        return new  CancelarConsultaUseCase(consultaRepo, presenter);
    }

    @Bean
    public IBuscarConsultaPorId buscarConsultaPorId(IConsultaRepository consultaRepo,
                                                    IConsultaPresenter presenter) {
        return new BuscarConsultaPorIdUseCase(consultaRepo, presenter);
    }

    @Bean
    public IListarConsultas listarConsultas(IConsultaRepository consultaRepo,
                                            IConsultaPresenter presenter) {
        return new ListarConsultasUseCase(consultaRepo, presenter);
    }
}
