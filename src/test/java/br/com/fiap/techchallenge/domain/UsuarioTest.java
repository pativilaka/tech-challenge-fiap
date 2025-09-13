package br.com.fiap.techchallenge.domain;

import br.com.fiap.techchallenge.domain.comum.DomainException;
import br.com.fiap.techchallenge.domain.comum.Endereco;
import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import br.com.fiap.techchallenge.domain.usuario.UsuarioFactoryBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UsuarioTest {

    private Endereco enderecoValido() {
        return Endereco.novoEndereco(
                "Rua A", "123", "Bairro B", "São Paulo", "SP", "01234-567", "Apto 1"
        );
    }

    private UsuarioFactoryBuilder builderValido() {
        return UsuarioFactoryBuilder.novo()
                .id(1L)
                .nome("Fulana da Silva")
                .cpf("000.000.000-00")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .email("fulana@exemplo.com")
                .telefone("11 99999-9999")
                .login("fulana")
                .senha("123456")
                .endereco(enderecoValido())
                .tipoUsuario(TipoUsuario.MEDICO);
    }

    @Test
    @DisplayName("Deve construir Usuario válido")
    void criarUsuario_sucesso() {
        var builder = builderValido();

        var usuario = builder.build();

        assertNotNull(usuario);
        assertEquals("Fulana da Silva", usuario.getNome());
        assertEquals("000.000.000-00", usuario.getCpf());
        assertEquals(LocalDate.of(1990,1,1), usuario.getDataNascimento());
        assertEquals("fulana@exemplo.com", usuario.getEmail());
        assertEquals("11 99999-9999", usuario.getTelefone());
        assertEquals("fulana", usuario.getLogin());
        assertEquals("123456", usuario.getSenha());
        assertEquals(TipoUsuario.MEDICO, usuario.getTipoUsuario());
        assertNotNull(usuario.getEndereco());
    }

    @Test
    @DisplayName("Não deve permitir nome em branco no construtor")
    void naoPermitirNomeEmBranco_noConstrutor() {
        var builder = builderValido().nome("  ");

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("nome"));
    }

    @Test
    @DisplayName("Não deve permitir e-mail inválido no construtor")
    void naoPermitirEmailInvalido_noConstrutor() {
        var builder = builderValido().email("invalido");

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("email"));
    }

    @Test
    @DisplayName("Não deve permitir CPF inválido no construtor")
    void naoPermitirCpfInvalido_noConstrutor() {
        var builder = builderValido().cpf("12345678900");

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("cpf"));
    }

    @Test
    @DisplayName("Não deve permitir telefone inválido no construtor")
    void naoPermitirTelefoneInvalido_noConstrutor() {
        var builder = builderValido().telefone("11 123-1234");

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("telefone"));
    }

    @Test
    @DisplayName("Não deve permitir endereço nulo no construtor")
    void naoPermitirEnderecoNulo_noConstrutor() {
        var builder = builderValido().endereco(null);

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("endereco"));
    }

    @Test
    @DisplayName("Não deve permitir tipo de usuário nulo no construtor")
    void naoPermitirTipoUsuarioNulo_noConstrutor() {
        var builder = builderValido().tipoUsuario(null);

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("tipo do usuário"));
    }

    @Test
    @DisplayName("Não deve permitir senha com menos de 6 caracteres no construtor")
    void naoPermitirSenhaCurta_noConstrutor() {
        var builder = builderValido().senha("123");

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("senha"));
    }

    @Test
    @DisplayName("alterarNome: sucesso")
    void alterarNome_sucesso() {
        var usuario = builderValido().build();

        usuario.alterarNome("Novo Nome");

        assertEquals("Novo Nome", usuario.getNome());
    }

    @Test
    @DisplayName("alterarEmail: sucesso")
    void alterarEmail_sucesso() {
        var usuario = builderValido().build();

        usuario.alterarEmail("novo@dominio.com");

        assertEquals("novo@dominio.com", usuario.getEmail());
    }

    @Test
    @DisplayName("alterarTelefone: sucesso")
    void alterarTelefone_sucesso() {
        var usuario = builderValido().build();

        usuario.alterarTelefone("11 3456-7890");

        assertEquals("11 3456-7890", usuario.getTelefone());
    }

    @Test
    @DisplayName("alterarSenha: sucesso")
    void alterarSenha_sucesso() {
        var usuario = builderValido().build();

        usuario.alterarSenha("nova123");

        assertEquals("nova123", usuario.getSenha());
    }

    @Test
    @DisplayName("alterarEndereco: sucesso")
    void alterarEndereco_sucesso() {
        var usuario = builderValido().build();
        var novo = Endereco.novoEndereco("Rua X","1","Bairro Y","SP","SP","11111-111","");

        usuario.alterarEndereco(novo);

        assertEquals(novo, usuario.getEndereco());
    }

    @Test
    @DisplayName("alterarTipoUsuario: sucesso")
    void alterarTipoUsuario_sucesso() {
        var usuario = builderValido().build();

        usuario.alterarTipoUsuario(TipoUsuario.PACIENTE);

        assertEquals(TipoUsuario.PACIENTE, usuario.getTipoUsuario());
    }

    @Test
    @DisplayName("alterarNome: inválido deve lançar e não alterar estado")
    void alterarNome_invalido_deveLancar() {
        var usuario = builderValido().build();
        var anterior = usuario.getNome();

        var ex = assertThrows(DomainException.class, () -> usuario.alterarNome("  "));

        assertTrue(ex.getMessage().contains("nome"));
        assertEquals(anterior, usuario.getNome());
    }

    @Test
    @DisplayName("alterarEmail: inválido deve lançar e não alterar estado")
    void alterarEmail_invalido_deveLancar() {
        var usuario = builderValido().build();
        var anterior = usuario.getEmail();

        var ex = assertThrows(DomainException.class, () -> usuario.alterarEmail("email@invalido"));

        assertTrue(ex.getMessage().contains("email"));
        assertEquals(anterior, usuario.getEmail());
    }

    @Test
    @DisplayName("alterarTelefone: inválido deve lançar e não alterar estado")
    void alterarTelefone_invalido_deveLancar() {
        var usuario = builderValido().build();
        var anterior = usuario.getTelefone();

        var ex = assertThrows(DomainException.class, () -> usuario.alterarTelefone("11 123-123"));

        assertTrue(ex.getMessage().contains("telefone"));
        assertEquals(anterior, usuario.getTelefone());
    }

    @Test
    @DisplayName("alterarSenha: inválida deve lançar e não alterar estado")
    void alterarSenha_invalida_deveLancar() {
        var usuario = builderValido().build();
        var anterior = usuario.getSenha();

        var ex = assertThrows(DomainException.class, () -> usuario.alterarSenha("123"));

        assertTrue(ex.getMessage().contains("senha"));
        assertEquals(anterior, usuario.getSenha());
    }

    @Test
    @DisplayName("alterarEndereco: nulo deve lançar e não alterar estado")
    void alterarEndereco_nulo_deveLancar() {
        var usuario = builderValido().build();
        var anterior = usuario.getEndereco();

        var ex = assertThrows(DomainException.class, () -> usuario.alterarEndereco(null));

        assertTrue(ex.getMessage().contains("endereco"));
        assertEquals(anterior, usuario.getEndereco());
    }

    @Test
    @DisplayName("alterarTipoUsuario: nulo deve lançar e não alterar estado")
    void alterarTipoUsuario_nulo_deveLancar() {
        var usuario = builderValido().build();
        var anterior = usuario.getTipoUsuario();

        var ex = assertThrows(DomainException.class, () -> usuario.alterarTipoUsuario(null));

        assertTrue(ex.getMessage().contains("tipoUsuario"));
        assertEquals(anterior, usuario.getTipoUsuario());
    }

    @Test
    @DisplayName("equals/hashCode devem considerar apenas o id")
    void equals_hashCode_porId() {
        var u1 = builderValido().id(10L).nome("A").build();
        var u2 = builderValido().id(10L).nome("B").build();

        boolean iguais = u1.equals(u2);

        assertTrue(iguais);
        assertEquals(u1.hashCode(), u2.hashCode());
    }
}
