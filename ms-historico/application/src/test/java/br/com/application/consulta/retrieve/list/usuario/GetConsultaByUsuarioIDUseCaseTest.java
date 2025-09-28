package br.com.application.consulta.retrieve.list.usuario;

import br.com.fiap.application.consulta.retrive.list.usuario.DefaultGetConsultaByUsuarioIdUseCase;
import br.com.fiap.application.usuario.retrieve.get.DefaultGetUsuarioByIdUseCase;
import br.com.fiap.domain.consulta.ConsultaGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetConsultaByUsuarioIDUseCaseTest {
    @InjectMocks
    private DefaultGetConsultaByUsuarioIdUseCase useCase;

    @Mock
    private ConsultaGateway consultaGateway;

    @BeforeEach
    void setUp() {
        Mockito.reset(consultaGateway);
    }

    @Test
    public void givenAValidId_whenCallGetConsulta_shouldReturnConsulta() {
        // given
        final var expectedUsuarioId = 123;

        Mockito.when(consultaGateway.findAllByUsuarioId(expectedUsuarioId))
                .thenReturn(Mockito.anyList());

        // when
        useCase.execute(expectedUsuarioId);

        // then
        Mockito.verify(consultaGateway, Mockito.times(1))
                .findAllByUsuarioId(expectedUsuarioId);

    }

}
