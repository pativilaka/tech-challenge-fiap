package br.com.fiap.techchallenge.application.usuario.mappers;

import br.com.fiap.techchallenge.application.usuario.comum.ApplicationException;
import br.com.fiap.techchallenge.application.usuario.dto.EnderecoApp;
import br.com.fiap.techchallenge.application.usuario.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.domain.usuario.*;

public final class UsuarioMapper {

    public UsuarioMapper() {
    }

    public static UsuarioResponseApp toApp(Usuario usuario){
        if (usuario == null) return null;

        String crm = null;
        String especialidade = null;
        String coren = null;
        String planoSaude = null;

        EnderecoApp enderecoApp = EnderecoMapper.toApp(usuario.getEndereco());

        switch (usuario) {
            case Medico m -> {
                crm = m.getCrm();
                especialidade = m.getEspecialidade();
            }
            case Enfermeiro e ->
                coren = e.getCoren();
            case Paciente p ->
                planoSaude = p.getPlanoSaude();
            default ->
                throw new ApplicationException("Tipo de usuário não suportado.");

        }

        return new UsuarioResponseApp(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getDataNascimento(),
                usuario.getEmail(),
                usuario.getTelefone(),
                enderecoApp,
                usuario.getTipoUsuario(),
                crm,
                especialidade,
                coren,
                planoSaude
        );
    }
}
