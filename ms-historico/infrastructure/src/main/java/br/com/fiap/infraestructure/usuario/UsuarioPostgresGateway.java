package br.com.fiap.infraestructure.usuario;

import br.com.fiap.domain.usuario.Usuario;
import br.com.fiap.domain.usuario.UsuarioGateway;
import br.com.fiap.domain.usuario.UsuarioID;
import br.com.fiap.infraestructure.usuario.persistence.UsuarioJpaEntity;
import br.com.fiap.infraestructure.usuario.persistence.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioPostgresGateway implements UsuarioGateway {

    private final UsuarioRepository usuarioRepository;

    public UsuarioPostgresGateway(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> findById(UsuarioID usuarioID) {
        return usuarioRepository.findById(usuarioID.getValue())
                .map(UsuarioJpaEntity::toAggregate);
    }
}
