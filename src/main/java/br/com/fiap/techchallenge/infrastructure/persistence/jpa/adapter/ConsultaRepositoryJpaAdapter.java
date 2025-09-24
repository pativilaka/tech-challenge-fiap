package br.com.fiap.techchallenge.infrastructure.persistence.jpa.adapter;

import br.com.fiap.techchallenge.application.consulta.ports.out.IConsultaRepository;
import br.com.fiap.techchallenge.domain.consulta.Consulta;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.mapper.ConsultaEntityMapper;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.repository.ConsultaJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConsultaRepositoryJpaAdapter implements IConsultaRepository {

    private final ConsultaJpaRepository jpa;
    private final ConsultaEntityMapper mapper;

    public ConsultaRepositoryJpaAdapter(ConsultaJpaRepository jpa, ConsultaEntityMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Consulta save(Consulta consulta) {
        var saved = jpa.save(mapper.toEntity(consulta));
        return mapper.toDomain(saved);
    }

    @Override
    public Consulta update(Consulta consulta) {
        var saved = jpa.save(mapper.toEntity(consulta));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Consulta> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Consulta> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(Long id) {
        if (jpa.existsById(id)) {
            jpa.deleteById(id);
            return true;
        }
        return false;
    }
}
