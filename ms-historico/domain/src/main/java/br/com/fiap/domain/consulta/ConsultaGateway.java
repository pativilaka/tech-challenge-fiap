package br.com.fiap.domain.consulta;

import java.util.List;

public interface ConsultaGateway {

    List<Consulta> findAllByUsuarioId(String pacienteId);

}
