package br.com.fiap.infraestructure.consulta;

import br.com.fiap.domain.consulta.Consulta;
import br.com.fiap.domain.consulta.ConsultaGateway;
import br.com.fiap.infraestructure.consulta.persistence.ConsultaJpaEntity;
import br.com.fiap.infraestructure.consulta.persistence.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaPostgresGateway implements ConsultaGateway {

    private final ConsultaRepository consultaRepository;

    public ConsultaPostgresGateway(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    @Override
    public List<Consulta> findAllByUsuarioId(Integer usuarioID) {
        return consultaRepository.findAllByUsuarioId(usuarioID)
                .stream().map(ConsultaJpaEntity::toAggregate).collect(Collectors.toList());
    }
}
