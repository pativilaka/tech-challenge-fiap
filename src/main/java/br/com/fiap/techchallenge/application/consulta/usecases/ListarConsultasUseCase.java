package br.com.fiap.techchallenge.application.consulta.usecases;

import br.com.fiap.techchallenge.application.consulta.dto.ListaConsultasResponseApp;
import br.com.fiap.techchallenge.application.consulta.mappers.ConsultaMapper;
import br.com.fiap.techchallenge.application.consulta.ports.in.IListarConsultas;
import br.com.fiap.techchallenge.application.consulta.ports.out.IConsultaRepository;
import br.com.fiap.techchallenge.application.consulta.ports.presenters.IConsultaPresenter;
import br.com.fiap.techchallenge.domain.consulta.Consulta;

import java.util.List;

public class ListarConsultasUseCase implements IListarConsultas {

    private final IConsultaRepository repository;
    private final IConsultaPresenter presenter;

    public ListarConsultasUseCase(IConsultaRepository repository, IConsultaPresenter presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    @Override
    public void execute() {

        List<Consulta> consultas = repository.findAll();
        ListaConsultasResponseApp consultasResponseApp = ConsultaMapper.listarConsultaApp(consultas);
        presenter.presentAll(consultasResponseApp);

    }
}
