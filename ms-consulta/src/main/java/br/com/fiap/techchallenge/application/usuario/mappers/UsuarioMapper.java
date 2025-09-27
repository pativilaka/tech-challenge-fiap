package br.com.fiap.techchallenge.application.usuario.mappers;

import br.com.fiap.techchallenge.application.comum.ApplicationException;
import br.com.fiap.techchallenge.application.usuario.dto.AtualizarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.usuario.dto.CriarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.usuario.dto.EnderecoApp;
import br.com.fiap.techchallenge.application.usuario.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.domain.comum.Endereco;
import br.com.fiap.techchallenge.domain.usuario.*;

public final class UsuarioMapper {

    private UsuarioMapper() {
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
                usuario.getLogin(),
                enderecoApp,
                usuario.getTipoUsuario(),
                crm,
                especialidade,
                coren,
                planoSaude
        );
    }

    public static Usuario toDomain(CriarUsuarioRequestApp requestApp){
        if (requestApp == null) return null;

        Endereco endereco = EnderecoMapper.toDomain(requestApp.endereco());

        return UsuarioFactoryBuilder.novo()
                .nome(requestApp.nome())
                .cpf(requestApp.cpf())
                .dataNascimento(requestApp.dataNascimento())
                .email(requestApp.email())
                .telefone(requestApp.telefone())
                .login(requestApp.login())
                .senha(requestApp.senha())
                .endereco(endereco)
                .tipoUsuario(requestApp.tipoUsuario())
                .crm(requestApp.crm())
                .especialidade(requestApp.especialidade())
                .coren(requestApp.coren())
                .planoSaude(requestApp.planoSaude())
                .build();

    }

    public static void toUpdateDomain(AtualizarUsuarioRequestApp requestApp, Usuario usuarioExistente){
        if (requestApp == null || usuarioExistente == null) return;

        if(requestApp.nome() != null) usuarioExistente.alterarNome(requestApp.nome());
        if(requestApp.email()!= null) usuarioExistente.alterarEmail(requestApp.email());
        if(requestApp.telefone() != null) usuarioExistente.alterarTelefone(requestApp.telefone());
        if(requestApp.senha() != null) usuarioExistente.alterarSenha(requestApp.senha());
        if(requestApp.endereco() != null) usuarioExistente.alterarEndereco(EnderecoMapper.toDomain(requestApp.endereco()));

        switch (usuarioExistente.getTipoUsuario()) {
            case MEDICO -> {
                if (usuarioExistente instanceof Medico m) {
                    if (requestApp.crm() != null) m.alterarCrm(requestApp.crm());
                    if (requestApp.especialidade() != null) m.alterarEspecialidade(requestApp.especialidade());
                }
            }
            case ENFERMEIRO -> {
                if (usuarioExistente instanceof Enfermeiro e) {
                    if (requestApp.coren() != null) e.alterarCoren(requestApp.coren());
                }
            }
            case PACIENTE -> {
                if (usuarioExistente instanceof Paciente p) {
                    if (requestApp.planoSaude() != null) p.alterarPlanoSaude(requestApp.planoSaude());
                }
            }
        }
    }
}
