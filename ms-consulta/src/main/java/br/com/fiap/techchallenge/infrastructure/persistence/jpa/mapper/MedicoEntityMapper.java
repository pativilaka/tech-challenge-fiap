package br.com.fiap.techchallenge.infrastructure.persistence.jpa.mapper;

import br.com.fiap.techchallenge.domain.comum.Endereco;
import br.com.fiap.techchallenge.domain.usuario.Medico;
import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import br.com.fiap.techchallenge.domain.usuario.UsuarioFactoryBuilder;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity.MedicoEntity;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity.UsuarioEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MedicoEntityMapper {
    public static Medico toDomain(MedicoEntity entity) {
        if (entity == null) return null;

        Endereco voEnd = entity.getEndereco() == null ? null :
                Endereco.novoEndereco(
                        entity.getEndereco().getLogradouro(),
                        entity.getEndereco().getNumero(),
                        entity.getEndereco().getBairro(),
                        entity.getEndereco().getCidade(),
                        entity.getEndereco().getUf(),
                        entity.getEndereco().getCep(),
                        entity.getEndereco().getComplemento()
                );

        return (Medico) UsuarioFactoryBuilder.novo()
                .id(entity.getId()).nome(entity.getNome()).cpf(entity.getCpf())
                .dataNascimento(entity.getDataNascimento()).email(entity.getEmail())
                .telefone(entity.getTelefone()).senha(entity.getSenha())
                .tipoUsuario(TipoUsuario.MEDICO).endereco(voEnd)
                .crm(entity.getCrm()).especialidade(entity.getEspecialidade())
                .build();
    }
}
