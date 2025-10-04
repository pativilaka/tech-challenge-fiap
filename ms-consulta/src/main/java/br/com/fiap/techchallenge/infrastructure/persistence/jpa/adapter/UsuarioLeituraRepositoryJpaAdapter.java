package br.com.fiap.techchallenge.infrastructure.persistence.jpa.adapter;

import br.com.fiap.techchallenge.application.consulta.ports.out.IUsuarioLeituraRepository;
import br.com.fiap.techchallenge.domain.usuario.Medico;
import br.com.fiap.techchallenge.domain.usuario.Paciente;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.mapper.MedicoEntityMapper;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.mapper.PacienteEntityMapper;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.repository.UsuarioJpaRepository;

import java.util.Optional;

public class UsuarioLeituraRepositoryJpaAdapter implements IUsuarioLeituraRepository {

    private final UsuarioJpaRepository jpa;

    public UsuarioLeituraRepositoryJpaAdapter(UsuarioJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpa.findById(id).map(UsuarioEntityMapper::toDomain);
    }

    @Override
    public Optional<Medico> findMedicoById(Long id) {
        return jpa.getMedicoByUsuarioId(id).map(MedicoEntityMapper::toDomain);
    }

    @Override
    public Optional<Paciente> findPacienteById(Long id) {
        return jpa.getPacienteByUsuarioId(id).map(PacienteEntityMapper::toDomain);
    }
}
