package br.com.application.usuario.retrieve.get;

import br.com.fiap.application.usuario.retrieve.get.DefaultGetUsuarioByIdUseCase;
import br.com.fiap.domain.exceptions.DomainException;
import br.com.fiap.domain.exceptions.NotFoundException;
import br.com.fiap.domain.paciente.Paciente;
import br.com.fiap.domain.usuario.CPF;
import br.com.fiap.domain.usuario.UsuarioGateway;
import br.com.fiap.domain.usuario.UsuarioID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetUsuarioByIdUseCaseTest {

    @InjectMocks
    private DefaultGetUsuarioByIdUseCase useCase;

    @Mock
    private UsuarioGateway usuarioGateway;

    @BeforeEach
    void setUp() {
        Mockito.reset(usuarioGateway);
    }

    @Test
    public void givenAValidId_whenCallGetUsuario_shouldReturnUsuario() {
        // given
        final var expectedNome = "João Silva";
        final var expectedCpf = new CPF("77149552013");
        final var expectedDataNascimento = LocalDate.of(1990, 1, 1);
        final var expectedEmail = "joao@email.com";
        final var expectedTipoUsuario = "PACIENTE";
        final var expectedSenha = "password123";
        final var expectedConvenioMedico = "SUS123456";

        final var anUsuario = Paciente.newPaciente(
                expectedNome,
                expectedCpf,
                expectedDataNascimento,
                expectedEmail,
                expectedSenha,
                expectedConvenioMedico
        );

        final var expectedId = anUsuario.getId();

        when(usuarioGateway.findById(expectedId))
                .thenReturn(Optional.of(anUsuario));

        final var expectedUsuario = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId, expectedUsuario.id());
        Assertions.assertEquals(expectedNome, expectedUsuario.nome());
        Assertions.assertEquals(expectedCpf, expectedUsuario.cpf());
        Assertions.assertEquals(expectedDataNascimento.toString(), expectedUsuario.dataNascimento());
        Assertions.assertEquals(expectedEmail, expectedUsuario.email());
        Assertions.assertEquals(expectedTipoUsuario, expectedUsuario.tipoUsuario());
        Mockito.verify(usuarioGateway, Mockito.times(1)).findById(expectedId);
    }

    @Test
    public void givenAValidId_whenGatewayThrowsError_shouldBeReturnException() {
        final var expectedId = UsuarioID.from("123");
        final var expectedMensagem = "Usuario não encontrado com o ID: 123";


        when(usuarioGateway.findById(eq(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedMensagem, actualException.getMessage());
        Mockito.verify(usuarioGateway, Mockito.times(1)).findById(Mockito.any());

    }

    @Test
    public void givenAnInvalidId_whenCallGetUsuario_shouldReturnNotFound() {
        final var expectedId = UsuarioID.from("123");
        final var expectedMensagem = "Gateway error";
        when(usuarioGateway.findById(eq(expectedId)))
                .thenThrow(new IllegalStateException(expectedMensagem));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue()));
        Assertions.assertEquals(expectedMensagem, actualException.getMessage());
        Mockito.verify(usuarioGateway, Mockito.times(1)).findById(Mockito.any());
    }

}
