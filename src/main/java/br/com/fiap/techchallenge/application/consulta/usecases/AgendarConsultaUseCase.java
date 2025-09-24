package br.com.fiap.techchallenge.application.consulta.usecases;

import br.com.fiap.techchallenge.application.comum.ApplicationException;
import br.com.fiap.techchallenge.application.consulta.dto.AgendarConsultaRequestApp;
import br.com.fiap.techchallenge.application.consulta.dto.ConsultaResponseApp;
import br.com.fiap.techchallenge.application.consulta.mappers.ConsultaMapper;
import br.com.fiap.techchallenge.application.consulta.ports.in.IAgendarConsulta;
import br.com.fiap.techchallenge.application.consulta.ports.out.IConsultaRepository;
import br.com.fiap.techchallenge.application.consulta.ports.out.IUsuarioLeituraRepository;
import br.com.fiap.techchallenge.application.consulta.ports.presenters.IConsultaPresenter;
import br.com.fiap.techchallenge.domain.comum.DomainException;
import br.com.fiap.techchallenge.domain.consulta.Consulta;
import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import br.com.fiap.techchallenge.domain.usuario.Usuario;

public class AgendarConsultaUseCase implements IAgendarConsulta {

    private final IConsultaRepository repository;
    private final IConsultaPresenter presenter;
    private final IUsuarioLeituraRepository usuarioLeituraRepository;

    public AgendarConsultaUseCase(IConsultaRepository repository, IConsultaPresenter presenter,
                                  IUsuarioLeituraRepository leituraRepository) {
        this.repository = repository;
        this.presenter = presenter;
        this.usuarioLeituraRepository = leituraRepository;
    }

    @Override
    public void execute(AgendarConsultaRequestApp requestApp) {

        if (requestApp == null) throw new ApplicationException("Request não pode ser nulo!");

        Long medicoId = requestApp.medicoId();
        Long pacienteId = requestApp.pacienteId();

        Usuario medico = usuarioLeituraRepository.findById(medicoId)
                .orElseThrow(() -> new DomainException("Médico não encontrado"));

        Usuario paciente = usuarioLeituraRepository.findById(pacienteId)
                .orElseThrow(() -> new DomainException("Paciente não encontrado"));

        if (medico.getTipoUsuario() != TipoUsuario.MEDICO) {
            throw new DomainException("ID informado não pertence a um MÉDICO");
        }
        if (paciente.getTipoUsuario() != TipoUsuario.PACIENTE) {
            throw new DomainException("ID informado não pertence a um PACIENTE");
        }

        Consulta consulta = ConsultaMapper.toDomain(requestApp);
        consulta = repository.save(consulta);

        ConsultaResponseApp responseApp = ConsultaMapper.toApp(consulta);
        presenter.present(responseApp);

    }
}
