package br.com.fiap.techchallenge.application.mappers;

import br.com.fiap.techchallenge.application.dto.AtualizarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.dto.CriarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.dto.EnderecoApp;
import br.com.fiap.techchallenge.application.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.domain.comum.Endereco;
import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import br.com.fiap.techchallenge.domain.usuario.UsuarioFactoryBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UsuarioMapperTest {
    @Test
    void deveConverterDomainParaResponseApp() {
        Endereco endereco = Endereco.novoEndereco("Rua A", "123", "Centro", "São Paulo", "SP", "00000-000", null);
        Usuario usuario = UsuarioFactoryBuilder.novo()
                .id(1L)
                .nome("Patrícia")
                .cpf("123.456.789-00")
                .dataNascimento(LocalDate.of(1990, 5, 10))
                .email("pati@ex.com")
                .telefone("11 99999-9999")
                .login("pati")
                .senha("segredo")
                .endereco(endereco)
                .tipoUsuario(TipoUsuario.PACIENTE)
                .build();

        UsuarioResponseApp dto = UsuarioMapper.toApp(usuario);

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals("Patrícia", dto.nome());
        assertEquals("123.456.789-00", dto.cpf());
        assertEquals("pati@ex.com", dto.email());
        assertEquals("11 99999-9999", dto.telefone());
        assertEquals("pati", dto.login());
        assertEquals(TipoUsuario.PACIENTE, dto.tipoUsuario());

        EnderecoApp endDto = dto.endereco();
        assertNotNull(endDto);
        assertEquals("Rua A", endDto.logradouro());
        assertEquals("123", endDto.numero());
        assertEquals("Centro", endDto.bairro());
    }

    @Test
    void deveConverterRequestAppParaDomain() {
        EnderecoApp enderecoApp = new EnderecoApp("Rua B", "456", "Bairro", "Rio", "RJ", "11111-000", "Ap 12");
        CriarUsuarioRequestApp req = new CriarUsuarioRequestApp(
                "João",
                "987.654.321-00",
                LocalDate.of(2000, 1, 1),
                "joao@ex.com",
                "21 99999-9999",
                "joao",
                "123456",
                enderecoApp,
                TipoUsuario.MEDICO
        );

        Usuario usuario = UsuarioMapper.toDomain(req);

        assertNotNull(usuario);
        assertEquals("João", usuario.getNome());
        assertEquals("987.654.321-00", usuario.getCpf());
        assertEquals("joao@ex.com", usuario.getEmail());
        assertEquals("21 99999-9999", usuario.getTelefone());
        assertEquals("joao", usuario.getLogin());
        assertEquals("123456", usuario.getSenha());
        assertEquals(TipoUsuario.MEDICO, usuario.getTipoUsuario());

        Endereco endereco = usuario.getEndereco();
        assertNotNull(endereco);
        assertEquals("Rua B", endereco.getLogradouro());
        assertEquals("456", endereco.getNumero());
    }

    @Test
    void deveAtualizarCamposDoUsuarioExistente() {
        Usuario existente = UsuarioFactoryBuilder.novo()
                .id(2L)
                .nome("Antigo")
                .cpf("000.000.000-00")
                .dataNascimento(LocalDate.of(1985, 10, 1))
                .email("antigo@ex.com")
                .telefone("11 1111-1111")
                .login("antigo")
                .senha("123456")
                .endereco(Endereco.novoEndereco("Rua Velha", "1", "Centro", "Cidade", "SP", "22222-000", null))
                .tipoUsuario(TipoUsuario.PACIENTE)
                .build();

        EnderecoApp novoEndereco = new EnderecoApp("Rua Nova", "99", "Bairro Novo", "Cidade Nova", "RJ", "33333-000", null);
        AtualizarUsuarioRequestApp req = new AtualizarUsuarioRequestApp(
                2L,
                "Novo Nome",
                null,
                null,
                "novo@ex.com",
                "22 2222-2222",
                null,
                "123456",
                novoEndereco,
                TipoUsuario.MEDICO
        );

        Usuario atualizado = UsuarioMapper.toUpdateDomain(req, existente);

        assertEquals("Novo Nome", atualizado.getNome());
        assertEquals("novo@ex.com", atualizado.getEmail());
        assertEquals("22 2222-2222", atualizado.getTelefone());
        assertEquals("123456", atualizado.getSenha());
        assertEquals(TipoUsuario.MEDICO, atualizado.getTipoUsuario());

        Endereco end = atualizado.getEndereco();
        assertEquals("Rua Nova", end.getLogradouro());
        assertEquals("99", end.getNumero());
    }

    @Test
    void deveRetornarNullQuandoEntradaForNull() {
        assertNull(UsuarioMapper.toApp(null));
        assertNull(UsuarioMapper.toDomain(null));

        var existenteValido = UsuarioFactoryBuilder.novo()
                .id(10L)
                .nome("Fulano")
                .cpf("000.000.000-00")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .email("fulano@ex.com")
                .telefone("11 1111-1111")
                .login("fulano")
                .senha("123456")
                .endereco(Endereco.novoEndereco("Rua", "1", "Bairro", "Cidade", "SP", "00000-000", null))
                .tipoUsuario(TipoUsuario.PACIENTE)
                .build();

        var resultado = UsuarioMapper.toUpdateDomain(null, existenteValido);
        assertNotNull(resultado);
        assertSame(existenteValido, resultado);

    }
}
