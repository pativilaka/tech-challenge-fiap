package br.com.fiap.techchallenge.application.mappers;

import br.com.fiap.techchallenge.application.dto.AtualizarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.dto.CriarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.dto.EnderecoApp;
import br.com.fiap.techchallenge.application.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.domain.comum.Endereco;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import br.com.fiap.techchallenge.domain.usuario.UsuarioFactoryBuilder;

public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static UsuarioResponseApp toApp(Usuario usuario){
            if (usuario == null) return null;

        EnderecoApp enderecoApp = EnderecoMapper.toApp(usuario.getEndereco());

        return new UsuarioResponseApp(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getDataNascimento(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getLogin(),
                enderecoApp,
                usuario.getTipoUsuario()
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
                .build();

    }

    public static Usuario toUpdateDomain(AtualizarUsuarioRequestApp requestApp, Usuario usuarioExistente){
        if (requestApp == null || usuarioExistente == null) return usuarioExistente;

        if(requestApp.nome() != null) usuarioExistente.alterarNome(requestApp.nome());
        if(requestApp.email()!= null) usuarioExistente.alterarEmail(requestApp.email());
        if(requestApp.telefone() != null) usuarioExistente.alterarTelefone(requestApp.telefone());
        if(requestApp.senha() != null) usuarioExistente.alterarSenha(requestApp.senha());
        if(requestApp.tipoUsuario() != null) usuarioExistente.alterarTipoUsuario(requestApp.tipoUsuario());
        if(requestApp.endereco() != null) usuarioExistente.alterarEndereco(EnderecoMapper.toDomain(requestApp.endereco()));

        return usuarioExistente;
    }
}
