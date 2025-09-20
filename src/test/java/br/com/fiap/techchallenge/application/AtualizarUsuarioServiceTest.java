package br.com.fiap.techchallenge.application;

import br.com.fiap.techchallenge.application.comum.ApplicationException;
import br.com.fiap.techchallenge.application.comum.NotFoundException;
import br.com.fiap.techchallenge.application.dto.AtualizarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.dto.EnderecoApp;
import br.com.fiap.techchallenge.application.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.application.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.ports.presenters.IUsuarioPresenter;
import br.com.fiap.techchallenge.application.service.AtualizarUsuarioService;
import br.com.fiap.techchallenge.domain.comum.Endereco;
import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import br.com.fiap.techchallenge.domain.usuario.UsuarioFactoryBuilder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AtualizarUsuarioServiceTest {
    @Mock
    IUsuarioRepository repository;
    @Mock
    IUsuarioPresenter presenter;

    @InjectMocks
    AtualizarUsuarioService service;

    @Test
    void deveAtualizarQuandoExiste() {

        var existente = UsuarioFactoryBuilder.novo()
                .id(5L).nome("Antigo").cpf("123.456.789-00").dataNascimento(LocalDate.of(1990,1,1))
                .email("old@ex.com").telefone("11 3456-7890").login("login").senha("xpto12")
                .endereco(Endereco.novoEndereco("R","1","B","C","SP","00000-000",null))
                .tipoUsuario(TipoUsuario.PACIENTE).planoSaude("Básico").build();

        when(repository.findById(5L)).thenReturn(Optional.of(existente));
        when(repository.update(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        var req = new AtualizarUsuarioRequestApp(
                5L, "Novo Nome", null, null, "novo@ex.com",
                "11 3456-9999", null, "123456",
                new EnderecoApp("Nova","2","B2","C2","SP","00000-000",null),
                TipoUsuario.PACIENTE, null, null, null, "Premium"
        );


        service.execute(req);

        verify(repository).findById(5L);
        verify(repository).update(argThat(u ->
                u.getId().equals(5L) &&
                        u.getNome().equals("Novo Nome") &&
                        u.getEmail().equals("novo@ex.com") &&
                        u.getTelefone().equals("11 3456-9999") &&
                        u.getSenha().equals("123456") &&
                        u.getTipoUsuario() == TipoUsuario.PACIENTE &&
                        u.getEndereco().getLogradouro().equals("Nova")
        ));
        verify(presenter).present(any(UsuarioResponseApp.class));
        verifyNoMoreInteractions(repository, presenter);
    }

    @Test
    void deveLancarNotFoundQuandoNaoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        var req = new AtualizarUsuarioRequestApp(99L, null,null,null,null,null,null,null,null,null, null, null, null,null);
        assertThrows(NotFoundException.class, () -> service.execute(req));
        verify(repository).findById(99L);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(presenter);
    }

    @Test
    void deveLancarApplicationExceptionQuandoRequestNulo() {
        assertThrows(ApplicationException.class, () -> service.execute(null));
        verifyNoInteractions(repository, presenter);
    }
}
