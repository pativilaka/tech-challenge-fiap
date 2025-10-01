package br.com.fiap.domain;

import br.com.fiap.domain.consulta.Consulta;
import br.com.fiap.domain.consulta.ConsultaID;
import br.com.fiap.domain.exceptions.DomainException;
import br.com.fiap.domain.usuario.UsuarioID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ConsultaTest {

    @Test
    public void deveReceberParametroValido_quandoCriarNovaConsulta_entaoRetorneUmaInstancia() {
        final var expectedId = ConsultaID.from(123);
        final var pacienteId = UsuarioID.from(123);
        final var medicoId = UsuarioID.from(123);
        final var dataHora = LocalDateTime.now().plusDays(1);
        final var status = "AGENDADA";

        final Consulta consulta = Consulta.newConsulta(expectedId, pacienteId, medicoId, dataHora, status);

        Assertions.assertNotNull(consulta);
        Assertions.assertEquals(pacienteId, consulta.getPacienteId());
        Assertions.assertEquals(medicoId, consulta.getMedicoId());
        Assertions.assertEquals(dataHora, consulta.getdataHoraConsulta());
        Assertions.assertEquals(status, consulta.getStatus());
    }

    @Test
    public void deveReceberStatusInvalido_quandoCriarConsulta_entaoRetorneUmaInstancia() {
        final var expectedId = ConsultaID.from(123);
        final var pacienteId = UsuarioID.from(123);
        final var medicoId = UsuarioID.from(123);
        final var dataHora = LocalDateTime.now().minusDays(1);
        final var status = "ERRADA";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> {
            Consulta.newConsulta(expectedId, pacienteId, medicoId, dataHora, status);
        });

        Assertions.assertEquals("Falha ao criar objeto usuário", actualException.getMessage());
        Assertions.assertEquals("status inválido", actualException.getErrors().getFirst().message());
        Assertions.assertEquals(1, actualException.getErrors().size());

    }

    @Test
    public void deveReceberMedicoIdNulo_quandoCriarConsulta_entaoRetorneUmaInstancia() {
        final var expectedId = ConsultaID.from(123);
        final var pacienteId = UsuarioID.from(123);
        final var dataHora = LocalDateTime.now().minusDays(1);
        final var status = "AGENDADA";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> {
            Consulta.newConsulta(expectedId, null, pacienteId, dataHora, status);
        });

        Assertions.assertEquals("Falha ao criar objeto usuário", actualException.getMessage());
        Assertions.assertEquals("medicoId não pode ser nulo", actualException.getErrors().getFirst().message());
        Assertions.assertEquals(1, actualException.getErrors().size());

    }

    @Test
    public void deveReceberStatusNulo_quandoCriarConsulta_entaoRetorneUmaInstancia() {
        final var expectedId = ConsultaID.from(123);
        final var pacienteId = UsuarioID.from(123);
        final var medicoId = UsuarioID.from(123);
        final var dataHora = LocalDateTime.now().plusDays(1);
        final String status = null;

        final var actualException = Assertions.assertThrows(DomainException.class, () -> {
            Consulta.newConsulta(expectedId, medicoId, pacienteId, dataHora, status);
        });

        Assertions.assertEquals("Falha ao criar objeto usuário", actualException.getMessage());
        Assertions.assertEquals("status não pode ser vazio", actualException.getErrors().getFirst().message());
        Assertions.assertEquals(1, actualException.getErrors().size());

    }


    @Test
    public void deveReceberStatusVazio_quandoCriarConsulta_entaoRetorneUmaInstancia() {
        final var expectedId = ConsultaID.from(123);
        final var pacienteId = UsuarioID.from(123);
        final var medicoId = UsuarioID.from(123);
        final var dataHora = LocalDateTime.now().minusDays(1);
        final var status = "";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> {
            Consulta.newConsulta(expectedId, medicoId, pacienteId, dataHora, status);
        });

        Assertions.assertEquals("Falha ao criar objeto usuário", actualException.getMessage());
        Assertions.assertEquals("status não pode ser vazio", actualException.getErrors().getFirst().message());
        Assertions.assertEquals(1, actualException.getErrors().size());

    }
}
