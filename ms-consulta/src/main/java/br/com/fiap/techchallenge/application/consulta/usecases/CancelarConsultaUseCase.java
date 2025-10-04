package br.com.fiap.techchallenge.application.consulta.usecases;

import br.com.fiap.techchallenge.application.consulta.dto.ConsultaResponseApp;
import br.com.fiap.techchallenge.application.consulta.mappers.ConsultaMapper;
import br.com.fiap.techchallenge.application.consulta.ports.in.ICancelarConsulta;
import br.com.fiap.techchallenge.application.consulta.ports.out.IConsultaProducer;
import br.com.fiap.techchallenge.application.consulta.ports.out.IConsultaRepository;
import br.com.fiap.techchallenge.application.consulta.ports.presenters.IConsultaPresenter;
import br.com.fiap.techchallenge.domain.comum.DomainException;
import br.com.fiap.techchallenge.domain.consulta.Consulta;

public class CancelarConsultaUseCase implements ICancelarConsulta {

    private final IConsultaRepository repository;
    private final IConsultaPresenter presenter;
    private final IConsultaProducer producer;

    public CancelarConsultaUseCase(IConsultaRepository repository, IConsultaPresenter presenter, IConsultaProducer producer) {
        this.repository = repository;
        this.presenter = presenter;
        this.producer = producer;
    }

    @Override
    public void execute(Long consultaId) {

        Consulta consulta = repository.findById(consultaId)
                .orElseThrow(() -> new DomainException("Consulta não encontrada"));

        consulta.cancelar();
        consulta = repository.update(consulta);

        producer.enviarEventoConsulta(consulta);

        ConsultaResponseApp responseApp = ConsultaMapper.toApp(consulta);
        presenter.present(responseApp);

    }
}
