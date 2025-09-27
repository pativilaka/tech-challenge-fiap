package br.com.fiap.domain;

import br.com.fiap.domain.enfermeiro.Enfermeiro;
import br.com.fiap.domain.exceptions.DomainException;
import br.com.fiap.domain.medico.Medico;
import br.com.fiap.domain.paciente.Paciente;
import br.com.fiap.domain.usuario.CPF;
import br.com.fiap.domain.usuario.UsuarioFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UsuarioTest {

    @Test
    public void deveReceberParametrosValidos_quandoCriarNovoUsuario_entaoInstancieUmUsuario() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("771.495.520-13");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "senha123";


        final var actualUsuario = UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        );

        Assertions.assertNotNull(actualUsuario);
        Assertions.assertNotNull(actualUsuario.getId());
        Assertions.assertEquals(expectedName, actualUsuario.getNome());
        Assertions.assertEquals(expectedCpf, actualUsuario.getCpf());
        Assertions.assertEquals(expectedDataNascimento, actualUsuario.getDataNascimento());
        Assertions.assertEquals(expectedEmail, actualUsuario.getEmail());
        Assertions.assertEquals(expectedSenha, actualUsuario.getSenha());
        Assertions.assertEquals(expectedTipoUsuario, actualUsuario.getTipoUsuario());
    }


    @Test
    public void deveReceberCpfInvalido_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("71587753300");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "senha123";


        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("CPF inválido", actualException.getErrors().getFirst().message());
    }

    @Test
    public void deveReceberCpfInvalidoCheck2_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("50403835401");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "senha123";


        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("CPF inválido", actualException.getErrors().getFirst().message());
    }

    @Test
    public void deveReceberCpfVazio_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "senha123";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("CPF inválido", actualException.getErrors().getFirst().message());

    }

    @Test
    public void deveReceberCpfComLetra_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("771.495.520-1A");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "senha123";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("CPF inválido", actualException.getErrors().getFirst().message());

    }

    @Test
    public void deveReceberCpfComOMesmoNumero_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("111.111.111-11");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "senha123";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("CPF inválido", actualException.getErrors().getFirst().message());

    }

    @Test
    public void deveReceberCpfNulo_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF(null);
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "senha123";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("CPF inválido", actualException.getErrors().getFirst().message());
    }

    @Test
    public void deveReceberNomeVazio_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "";
        final var expectedCpf = new CPF("771.495.520-13");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "senha123";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("Nome não pode ser vazio", actualException.getErrors().getFirst().message());
    }

    @Test
    public void deveReceberNomeNulo_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedCpf = new CPF("771.495.520-13");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "senha123";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                null,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("Nome não pode ser vazio", actualException.getErrors().getFirst().message());
    }

    @Test
    public void deveReceberEmailInvalido_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("771.495.520-13");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joaoemail.com";
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "senha123";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("Email inválido", actualException.getErrors().getFirst().message());
    }

    @Test
    public void deveReceberEmailNulo_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("771.495.520-13");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "senha123";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                null,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("Email inválido", actualException.getErrors().getFirst().message());
    }

    @Test
    public void deveReceberSenhaVazia_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("771.495.520-13");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("Senha não pode ser vazia", actualException.getErrors().getFirst().message());
    }

    @Test
    public void deveReceberSenhaNula_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("771.495.520-13");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "MEDICO";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                null,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("Senha não pode ser vazia", actualException.getErrors().getFirst().message());
    }

    @Test
    public void deveReceberTipoUsuarioInvalido_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("771.495.520-13");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "GERENTE";
        final var expectedSenha = "senha123";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("Tipo de usuário inválido", actualException.getErrors().getFirst().message());
    }

    @Test
    public void deveReceberTipoUsuarioVazio_quandoCriarNovoUsuario_entaoDeveRetornarErro() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("771.495.520-13");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedSenha = "senha123";
        final var expectedTipoUsuario = "";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> UsuarioFactory.createUsuario(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedTipoUsuario,
                "CR12345",
                "Cardiologista",
                null,
                null
        ));
        Assertions.assertEquals(1, actualException.getErrors().size());
        Assertions.assertEquals("Tipo de usuário não pode ser vazio", actualException.getErrors().getFirst().message());
    }

    @Test
    public void deveReceberParametroValidos_quandoCriarNovoUsuarioDoTipoEnfermeiro_entaoInstancieUmUsuario() {
        final var expectedName = "Ana Maria";
        final var expectedCpf = new CPF("123.456.789-09");
        final var expectedDataNascimento = LocalDate.of(1985, 8, 15);
        final var expectedEmail = "ana@email.com";
        final var expectedTipoUsuario = "ENFERMEIRO";
        final var expectedSenha = "senha456";
        final var expectedCoren = "1234567890";

        final var actualUsuario = Enfermeiro.newPaciente(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedCoren
        );

        Assertions.assertNotNull(actualUsuario);
        Assertions.assertNotNull(actualUsuario.getId());
        Assertions.assertEquals(expectedName, actualUsuario.getNome());
        Assertions.assertEquals(expectedCpf, actualUsuario.getCpf());
        Assertions.assertEquals(expectedDataNascimento, actualUsuario.getDataNascimento());
        Assertions.assertEquals(expectedEmail, actualUsuario.getEmail());
        Assertions.assertEquals(expectedSenha, actualUsuario.getSenha());
        Assertions.assertEquals(expectedTipoUsuario, actualUsuario.getTipoUsuario());
        Assertions.assertEquals(expectedCoren, actualUsuario.getCoren());
    }

    @Test
    public void deveReceberParametroValidos_quandoCriarNovoUsuarioDoTipoPaciente_entaoInstancieUmUsuario() {
        final var expectedName = "Ana Maria";
        final var expectedCpf = new CPF("123.456.789-09");
        final var expectedDataNascimento = LocalDate.of(1985, 8, 15);
        final var expectedEmail = "ana@email.com";
        final var expectedSenha = "senha456";
        final var expectedTipoUsuario = "PACIENTE";
        final var expectedConvenioMedico = "Bradesco saúde";

        final var actualPaciente = Paciente.newPaciente(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedConvenioMedico
        );

        Assertions.assertNotNull(actualPaciente);
        Assertions.assertNotNull(actualPaciente.getId());
        Assertions.assertEquals(expectedName, actualPaciente.getNome());
        Assertions.assertEquals(expectedCpf, actualPaciente.getCpf());
        Assertions.assertEquals(expectedDataNascimento, actualPaciente.getDataNascimento());
        Assertions.assertEquals(expectedEmail, actualPaciente.getEmail());
        Assertions.assertEquals(expectedSenha, actualPaciente.getSenha());
        Assertions.assertEquals(expectedTipoUsuario, actualPaciente.getTipoUsuario());
        Assertions.assertEquals(expectedConvenioMedico, actualPaciente.getConvenioMedico());
    }

    @Test
    public void deveReceberParametrosValidos_quandoCriarNovoUsuarioDoTipoMedico_entaoInstancieUmUsuario() {
        final var expectedName = "João Silva";
        final var expectedCpf = new CPF("77149552013");
        final var expectedDataNascimento = LocalDate.of(1990, 5, 20);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "MEDICO";
        final var expectedSenha = "senha123";
        final var expectedCrm = "CRM123456";
        final var expectedEspecialidade = "Cardiologista";

        final var actualUsuario = Medico.newMedico(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedCrm,
                expectedEspecialidade
        );

        Assertions.assertNotNull(actualUsuario);
        Assertions.assertNotNull(actualUsuario.getId());
        Assertions.assertEquals(expectedName, actualUsuario.getNome());
        Assertions.assertEquals(expectedCpf, actualUsuario.getCpf());
        Assertions.assertEquals(expectedDataNascimento, actualUsuario.getDataNascimento());
        Assertions.assertEquals(expectedEmail, actualUsuario.getEmail());
        Assertions.assertEquals(expectedSenha, actualUsuario.getSenha());
        Assertions.assertEquals(expectedTipoUsuario, actualUsuario.getTipoUsuario());
        Assertions.assertEquals(expectedCrm, actualUsuario.getCrm());
        Assertions.assertEquals(expectedEspecialidade, actualUsuario.getEspecialidade());
    }
}
