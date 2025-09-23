package br.com.fiap.techchallenge.application.consulta.usecases;

import br.com.fiap.techchallenge.application.consulta.dto.ConsultaResponseApp;
import br.com.fiap.techchallenge.application.consulta.mappers.ConsultaMapper;
import br.com.fiap.techchallenge.application.consulta.ports.in.IBuscarConsultaPorId;
import br.com.fiap.techchallenge.application.consulta.ports.out.IConsultaRepository;
import br.com.fiap.techchallenge.application.consulta.ports.presenters.IConsultaPresenter;
import br.com.fiap.techchallenge.domain.comum.DomainException;
import br.com.fiap.techchallenge.domain.consulta.Consulta;

public class BuscarConsultaPorIdUseCase implements IBuscarConsultaPorId {

    private final IConsultaRepository repository;
    private final IConsultaPresenter presenter;

    public BuscarConsultaPorIdUseCase(IConsultaRepository repository, IConsultaPresenter presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    @Override
    public void executar(Long consultaId) {
        Consulta consulta = repository.findById(consultaId)
                .orElseThrow(() -> new DomainException("Consulta não encontrada"));

        ConsultaResponseApp responseApp = ConsultaMapper.toApp(consulta);
        presenter.present(responseApp);
    }
}
