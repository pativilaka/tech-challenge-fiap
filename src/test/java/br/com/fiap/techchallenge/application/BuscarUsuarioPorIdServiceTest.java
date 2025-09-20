package br.com.fiap.techchallenge.application;

import br.com.fiap.techchallenge.application.comum.ApplicationException;
import br.com.fiap.techchallenge.application.comum.NotFoundException;
import br.com.fiap.techchallenge.application.dto.EnderecoApp;
import br.com.fiap.techchallenge.application.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.application.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.ports.presenters.IUsuarioPresenter;
import br.com.fiap.techchallenge.application.service.BuscarUsuarioPorIdService;
import br.com.fiap.techchallenge.domain.comum.Endereco;
import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import br.com.fiap.techchallenge.domain.usuario.UsuarioFactoryBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BuscarUsuarioPorIdServiceTest {

    @Mock
    IUsuarioRepository repository;
    @Mock
    IUsuarioPresenter presenter;

    @InjectMocks
    BuscarUsuarioPorIdService service;

    @Test
    void deveApresentarQuandoEncontrar() {
        Usuario existente = UsuarioFactoryBuilder.novo()
                .id(42L)
                .nome("Patrícia")
                .cpf("123.456.789-00")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .email("pati@ex.com")
                .telefone("11 99999-9999")
                .login("patricia")
                .senha("123456")
                .endereco(Endereco.novoEndereco("Rua A","10","Centro","São Paulo","SP","01000-000", null))
                .tipoUsuario(TipoUsuario.PACIENTE)
                .planoSaude("Básico")
                .build();

        when(repository.findById(42L)).thenReturn(Optional.of(existente));

        // Act
        service.execute(42L);

        // Assert: chamou presenter.present com o Response certo
        ArgumentCaptor<UsuarioResponseApp> captor = ArgumentCaptor.forClass(UsuarioResponseApp.class);
        verify(presenter, times(1)).present(captor.capture());
        UsuarioResponseApp resp = captor.getValue();

        assertEquals(42L, resp.id());
        assertEquals("Patrícia", resp.nome());
        assertEquals("123.456.789-00", resp.cpf());
        assertEquals(LocalDate.of(1990,1,1), resp.dataNascimento());
        assertEquals("pati@ex.com", resp.email());
        assertEquals("11 99999-9999", resp.telefone());
        assertEquals("patricia", resp.login());
        assertEquals(TipoUsuario.PACIENTE, resp.tipoUsuario());
        assertEquals("Básico", resp.planoSaude());

        EnderecoApp e = resp.endereco();
        assertNotNull(e);
        assertEquals("Rua A", e.logradouro());
        assertEquals("10", e.numero());
        assertEquals("Centro", e.bairro());
        assertEquals("São Paulo", e.cidade());
        assertEquals("SP", e.uf());
        assertEquals("01000-000", e.cep());

        verify(repository, times(1)).findById(42L);
        verifyNoMoreInteractions(repository, presenter);
    }

    @Test
    void deveLancarApplicationExceptionQuandoIdNulo() {
        // Act + Assert
        assertThrows(ApplicationException.class, () -> service.execute(null));
        verifyNoInteractions(repository, presenter);
    }

    @Test
    void deveLancarNotFoundQuandoNaoEncontrar() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(NotFoundException.class, () -> service.execute(99L));

        verify(repository, times(1)).findById(99L);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(presenter);
    }


}
