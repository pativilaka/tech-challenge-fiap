package br.com.fiap.infraestructure.usuario.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioJpaEntity, Integer> {
}
