package br.com.fiap.techchallenge.application.consulta.ports.out;

import br.com.fiap.techchallenge.domain.consulta.Consulta;

import java.util.List;
import java.util.Optional;

public interface IConsultaRepository {

    Consulta save(Consulta consulta);
    Consulta update(Consulta consulta);
    Optional<Consulta> findById(Long id);
    List<Consulta> findAll();
    boolean deleteById(Long id);

}
