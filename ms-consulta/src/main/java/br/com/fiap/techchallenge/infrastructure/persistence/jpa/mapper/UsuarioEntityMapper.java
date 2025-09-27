package br.com.fiap.techchallenge.infrastructure.persistence.jpa.mapper;

import br.com.fiap.techchallenge.domain.comum.Endereco;
import br.com.fiap.techchallenge.domain.usuario.*;
import br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class    UsuarioEntityMapper {

    public static UsuarioEntity toEntity(Usuario domain) {
        if (domain == null) return null;

        EnderecoEmbeddable end = domain.getEndereco() == null ? null :
                new EnderecoEmbeddable(
                        domain.getEndereco().getLogradouro(),
                        domain.getEndereco().getNumero(),
                        domain.getEndereco().getBairro(),
                        domain.getEndereco().getCidade(),
                        domain.getEndereco().getUf(),
                        domain.getEndereco().getCep(),
                        domain.getEndereco().getComplemento()
                );

        return switch (domain) {
            case Medico m -> new MedicoEntity(
                    m.getId(), m.getNome(), m.getCpf(), m.getDataNascimento(),
                    m.getEmail(), m.getTelefone(), m.getSenha(),
                    end, m.getCrm(), m.getEspecialidade()
            );
            case Enfermeiro e -> new EnfermeiroEntity(
                    e.getId(), e.getNome(), e.getCpf(), e.getDataNascimento(),
                    e.getEmail(), e.getTelefone(), e.getSenha(),
                    end, e.getCoren()
            );
            case Paciente p -> new PacienteEntity(
                    p.getId(), p.getNome(), p.getCpf(), p.getDataNascimento(),
                    p.getEmail(), p.getTelefone(), p.getSenha(),
                    end, p.getPlanoSaude()
            );
            default -> throw new IllegalStateException("Tipo de usuário desconhecido: " + domain.getClass());
        };
    }

    public static Usuario toDomain(UsuarioEntity entity) {
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

        return switch (entity) {
            case MedicoEntity m -> UsuarioFactoryBuilder.novo()
                    .id(m.getId()).nome(m.getNome()).cpf(m.getCpf())
                    .dataNascimento(m.getDataNascimento()).email(m.getEmail())
                    .telefone(m.getTelefone()).senha(m.getSenha())
                    .tipoUsuario(TipoUsuario.MEDICO).endereco(voEnd)
                    .crm(m.getCrm()).especialidade(m.getEspecialidade())
                    .build();

            case EnfermeiroEntity e -> UsuarioFactoryBuilder.novo()
                    .id(e.getId()).nome(e.getNome()).cpf(e.getCpf())
                    .dataNascimento(e.getDataNascimento()).email(e.getEmail())
                    .telefone(e.getTelefone()).senha(e.getSenha())
                    .tipoUsuario(TipoUsuario.ENFERMEIRO).endereco(voEnd)
                    .coren(e.getCoren())
                    .build();

            case PacienteEntity p -> UsuarioFactoryBuilder.novo()
                    .id(p.getId()).nome(p.getNome()).cpf(p.getCpf())
                    .dataNascimento(p.getDataNascimento()).email(p.getEmail())
                    .telefone(p.getTelefone()).senha(p.getSenha())
                    .tipoUsuario(TipoUsuario.PACIENTE).endereco(voEnd)
                    .planoSaude(p.getPlanoSaude())
                    .build();

            case null -> null;
            default -> throw new IllegalStateException("Tipo de entity desconhecido: " + entity.getClass());
        };
    }

}
