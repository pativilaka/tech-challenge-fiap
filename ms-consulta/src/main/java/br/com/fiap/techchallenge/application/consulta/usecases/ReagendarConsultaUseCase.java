package br.com.fiap.techchallenge.application.consulta.usecases;

import br.com.fiap.techchallenge.application.comum.ApplicationException;
import br.com.fiap.techchallenge.application.consulta.dto.ConsultaResponseApp;
import br.com.fiap.techchallenge.application.consulta.dto.ReagendarConsultaRequestApp;
import br.com.fiap.techchallenge.application.consulta.mappers.ConsultaMapper;
import br.com.fiap.techchallenge.application.consulta.ports.in.IReagendarConsulta;
import br.com.fiap.techchallenge.application.consulta.ports.out.IConsultaProducer;
import br.com.fiap.techchallenge.application.consulta.ports.out.IConsultaRepository;
import br.com.fiap.techchallenge.application.consulta.ports.presenters.IConsultaPresenter;
import br.com.fiap.techchallenge.domain.comum.DomainException;
import br.com.fiap.techchallenge.domain.consulta.Consulta;

public class ReagendarConsultaUseCase implements IReagendarConsulta {

    private final IConsultaRepository repository;
    private final IConsultaPresenter presenter;
    private final IConsultaProducer producer;

    public ReagendarConsultaUseCase(IConsultaRepository repository, IConsultaPresenter presenter, IConsultaProducer producer) {
        this.repository = repository;
        this.presenter = presenter;
        this.producer = producer;
    }

    @Override
    public void execute(ReagendarConsultaRequestApp requestApp) {

        if (requestApp == null) throw new ApplicationException("Request não pode ser nulo!");

        Consulta consulta = repository.findById(requestApp.consultaId())
                .orElseThrow(() -> new DomainException("Consulta não encontrada"));

        consulta.reagendar(requestApp.novoInicio(), requestApp.novoFim());
        consulta = repository.update(consulta);
        this.producer.enviarEventoConsulta(consulta);
        ConsultaResponseApp responseApp = ConsultaMapper.toApp(consulta);
        presenter.present(responseApp);

    }
}
