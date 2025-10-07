package br.com.fiap.techchallenge.domain;

import br.com.fiap.techchallenge.domain.comum.DomainException;
import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import br.com.fiap.techchallenge.domain.usuario.UsuarioFactoryBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UsuarioTest {

    private UsuarioFactoryBuilder builderMedico() {
        return UsuarioFactoryBuilder.novo()
                .id(1L)
                .nome("Fulana da Silva")
                .cpf("000.000.000-00")
                .dataNascimento(LocalDate.of(1990,1,1))
                .email("fulana@exemplo.com")
                .telefone("11 99999-9999")
                .senha("123456")
                .tipoUsuario(TipoUsuario.MEDICO)
                .crm("CRM-123")
                .especialidade("CLÍNICA");
    }

    private UsuarioFactoryBuilder builderEnfermeira() {
        return UsuarioFactoryBuilder.novo()
                .nome("Enfa. Ana")
                .cpf("000.000.000-00")
                .dataNascimento(LocalDate.of(1990,1,1))
                .email("ana@exemplo.com")
                .telefone("11 99999-9999")
                .senha("123456")
                .tipoUsuario(TipoUsuario.ENFERMEIRO)
                .coren("COREN-999");
    }

    private UsuarioFactoryBuilder builderPaciente() {
        return UsuarioFactoryBuilder.novo()
                .nome("Paciente João")
                .cpf("000.000.000-00")
                .dataNascimento(LocalDate.of(1990,1,1))
                .email("joao@exemplo.com")
                .telefone("11 99999-9999")
                .senha("123456")
                .tipoUsuario(TipoUsuario.PACIENTE)
                .planoSaude("MeuPlano");
    }

    @Test
    @DisplayName("Medico deve exigir crm e especialidade")
    void medico_criacao_sucesso() {
        var u = builderMedico().build();
        assertEquals(TipoUsuario.MEDICO, u.getTipoUsuario());
    }

    @Test
    @DisplayName("Medico sem crm deve lançar")
    void medico_sem_crm() {
        var b = builderMedico().crm(null);
        assertThrows(DomainException.class, b::build);
    }

    @Test
    @DisplayName("Enfermeira deve exigir coren")
    void enfermeira_sem_coren() {
        var b = builderEnfermeira().coren(null);
        assertThrows(DomainException.class, b::build);
    }

    @Test
    @DisplayName("Paciente deve exigir plano de saúde")
    void paciente_sem_plano() {
        var b = builderPaciente().planoSaude(null);
        assertThrows(DomainException.class, b::build);
    }

    @Test
    @DisplayName("Não deve permitir nome em branco no construtor")
    void naoPermitirNomeEmBranco_noConstrutor() {
        var builder = builderPaciente().nome("  ");

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("nome"));
    }

    @Test
    @DisplayName("Não deve permitir e-mail inválido no construtor")
    void naoPermitirEmailInvalido_noConstrutor() {
        var builder = builderPaciente().email("invalido");

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("email"));
    }

    @Test
    @DisplayName("Não deve permitir CPF inválido no construtor")
    void naoPermitirCpfInvalido_noConstrutor() {
        var builder = builderPaciente().cpf("123456789001");

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("cpf"));
    }

    @Test
    @DisplayName("Não deve permitir telefone inválido no construtor")
    void naoPermitirTelefoneInvalido_noConstrutor() {
        var builder = builderPaciente().telefone("11 123-1234");

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("telefone"));
    }

    @Test
    @DisplayName("Não deve permitir tipo de usuário nulo no construtor")
    void naoPermitirTipoUsuarioNulo_noConstrutor() {
        var builder = builderPaciente().tipoUsuario(null);

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("tipoUsuario"));
    }

    @Test
    @DisplayName("Não deve permitir senha com menos de 6 caracteres no construtor")
    void naoPermitirSenhaCurta_noConstrutor() {
        var builder = builderPaciente().senha("123");

        var ex = assertThrows(DomainException.class, builder::build);

        assertTrue(ex.getMessage().contains("senha"));
    }

    @Test
    @DisplayName("alterarNome: sucesso")
    void alterarNome_sucesso() {
        var usuario = builderPaciente().build();

        usuario.alterarNome("Novo Nome");

        assertEquals("Novo Nome", usuario.getNome());
    }

    @Test
    @DisplayName("alterarEmail: sucesso")
    void alterarEmail_sucesso() {
        var usuario = builderPaciente().build();

        usuario.alterarEmail("novo@dominio.com");

        assertEquals("novo@dominio.com", usuario.getEmail());
    }

    @Test
    @DisplayName("alterarTelefone: sucesso")
    void alterarTelefone_sucesso() {
        var usuario = builderPaciente().build();

        usuario.alterarTelefone("11 3456-7890");

        assertEquals("11 3456-7890", usuario.getTelefone());
    }

    @Test
    @DisplayName("alterarSenha: sucesso")
    void alterarSenha_sucesso() {
        var usuario = builderPaciente().build();

        usuario.alterarSenha("nova123");

        assertEquals("nova123", usuario.getSenha());
    }

    @Test
    @DisplayName("alterarTipoUsuario: sucesso")
    void alterarTipoUsuario_sucesso() {
        var usuario = builderPaciente().build();

        usuario.alterarTipoUsuario(TipoUsuario.PACIENTE);

        assertEquals(TipoUsuario.PACIENTE, usuario.getTipoUsuario());
    }

    @Test
    @DisplayName("alterarNome: inválido deve lançar e não alterar estado")
    void alterarNome_invalido_deveLancar() {
        var usuario = builderPaciente().build();
        var anterior = usuario.getNome();

        var ex = assertThrows(DomainException.class, () -> usuario.alterarNome("  "));

        assertTrue(ex.getMessage().contains("nome"));
        assertEquals(anterior, usuario.getNome());
    }

    @Test
    @DisplayName("alterarEmail: inválido deve lançar e não alterar estado")
    void alterarEmail_invalido_deveLancar() {
        var usuario = builderPaciente().build();
        var anterior = usuario.getEmail();

        var ex = assertThrows(DomainException.class, () -> usuario.alterarEmail("email@invalido"));

        assertTrue(ex.getMessage().contains("email"));
        assertEquals(anterior, usuario.getEmail());
    }

    @Test
    @DisplayName("alterarTelefone: inválido deve lançar e não alterar estado")
    void alterarTelefone_invalido_deveLancar() {
        var usuario = builderPaciente().build();
        var anterior = usuario.getTelefone();

        var ex = assertThrows(DomainException.class, () -> usuario.alterarTelefone("11 123-123"));

        assertTrue(ex.getMessage().contains("telefone"));
        assertEquals(anterior, usuario.getTelefone());
    }

    @Test
    @DisplayName("alterarSenha: inválida deve lançar e não alterar estado")
    void alterarSenha_invalida_deveLancar() {
        var usuario = builderPaciente().build();
        var anterior = usuario.getSenha();

        var ex = assertThrows(DomainException.class, () -> usuario.alterarSenha("123"));

        assertTrue(ex.getMessage().contains("senha"));
        assertEquals(anterior, usuario.getSenha());
    }

    @Test
    @DisplayName("alterarTipoUsuario: nulo deve lançar e não alterar estado")
    void alterarTipoUsuario_nulo_deveLancar() {
        var usuario = builderPaciente().build();
        var anterior = usuario.getTipoUsuario();

        var ex = assertThrows(DomainException.class, () -> usuario.alterarTipoUsuario(null));

        assertTrue(ex.getMessage().contains("tipoUsuario"));
        assertEquals(anterior, usuario.getTipoUsuario());
    }

    @Test
    @DisplayName("equals/hashCode devem considerar apenas o id")
    void equals_hashCode_porId() {
        var u1 = builderPaciente().id(10L).nome("A").build();
        var u2 = builderPaciente().id(10L).nome("B").build();

        boolean iguais = u1.equals(u2);

        assertTrue(iguais);
        assertEquals(u1.hashCode(), u2.hashCode());
    }
}
