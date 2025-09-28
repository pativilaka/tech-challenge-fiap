package br.com.fiap.application.consulta.retrive.list.usuario;

import br.com.fiap.domain.consulta.ConsultaGateway;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultGetConsultaByUsuarioIdUseCase extends GetConsultaByUsuarioIdUseCase {

    private final ConsultaGateway consultaGateway;

    public DefaultGetConsultaByUsuarioIdUseCase(final ConsultaGateway consultaGateway) {
        this.consultaGateway = Objects.requireNonNull(consultaGateway);
    }

    @Override
    public List<ListConsultaOutput> execute(Integer usuarioId) {
        return this.consultaGateway.findAllByUsuarioId(usuarioId)
                .stream().map(ListConsultaOutput::from).collect(Collectors.toList());
    }
}
