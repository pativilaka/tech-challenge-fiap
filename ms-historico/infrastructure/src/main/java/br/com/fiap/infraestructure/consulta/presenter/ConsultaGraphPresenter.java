package br.com.fiap.infraestructure.consulta.presenter;

import br.com.fiap.application.consulta.retrive.list.usuario.ListConsultaOutput;
import br.com.fiap.application.usuario.retrieve.get.UsuarioOutput;
import br.com.fiap.domain.usuario.Usuario;
import br.com.fiap.infraestructure.consulta.model.ConsultaResponse;

public interface ConsultaGraphPresenter {
    static ConsultaResponse consultaResponse(final ListConsultaOutput consultaOutput,
                                             final UsuarioOutput paciente,
                                             final UsuarioOutput medico) {
        return new ConsultaResponse(
                consultaOutput.id().getValue(),
                consultaOutput.dataHora(),
                new ConsultaResponse.UsuarioResponse(
                        paciente.id().getValue(),
                        paciente.nome(),
                        paciente.email()
                ),
                new ConsultaResponse.UsuarioResponse(
                        medico.id().getValue(),
                        medico.nome(),
                        medico.email()
                ),
                consultaOutput.status()
        );
    }
}
