package br.com.fiap.techchallenge.application.consulta.usecases;

import br.com.fiap.techchallenge.application.comum.ApplicationException;
import br.com.fiap.techchallenge.application.consulta.dto.AgendarConsultaRequestApp;
import br.com.fiap.techchallenge.application.consulta.dto.ConsultaResponseApp;
import br.com.fiap.techchallenge.application.consulta.mappers.ConsultaMapper;
import br.com.fiap.techchallenge.application.consulta.ports.in.IAgendarConsulta;
import br.com.fiap.techchallenge.application.consulta.ports.out.IConsultaRepository;
import br.com.fiap.techchallenge.application.consulta.ports.presenters.IConsultaPresenter;
import br.com.fiap.techchallenge.domain.consulta.Consulta;

public class AgendarConsultaUseCase implements IAgendarConsulta {

    private final IConsultaRepository repository;
    private final IConsultaPresenter presenter;

    public AgendarConsultaUseCase(IConsultaRepository repository, IConsultaPresenter presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    @Override
    public void execute(AgendarConsultaRequestApp requestApp) {

        if (requestApp == null) throw new ApplicationException("Request não pode ser nulo!");

        Consulta consulta = ConsultaMapper.toDomain(requestApp);
        consulta = repository.save(consulta);

        ConsultaResponseApp responseApp = ConsultaMapper.toApp(consulta);
        presenter.present(responseApp);

    }
}
