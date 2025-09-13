package br.com.fiap.techchallenge.application;

import br.com.fiap.techchallenge.application.comum.ApplicationException;
import br.com.fiap.techchallenge.application.comum.NotFoundExceptiion;
import br.com.fiap.techchallenge.application.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.ports.presenters.IUsuarioPresenter;
import br.com.fiap.techchallenge.application.service.DeletarUsuarioService;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeletarUsuarioServiceTest {

    @Mock
    IUsuarioRepository repository;
    @Mock
    IUsuarioPresenter presenter;

    @InjectMocks
    DeletarUsuarioService service;

    @Test
    void deveDeletarQuandoTrue() {
        when(repository.deleteById(7L)).thenReturn(true);
        service.execute(7L);
        verify(presenter).deleted(7L);
    }

    @Test
    void deveLancarQuandoFalse() {
        when(repository.deleteById(8L)).thenReturn(false);
        assertThrows(NotFoundExceptiion.class, () -> service.execute(8L));
    }

    @Test
    void deveLancarQuandoIdNulo() {
        assertThrows(ApplicationException.class, () -> service.execute(null));
        verifyNoInteractions(repository, presenter);
    }
}
