package br.com.fiap.techchallenge.application;

import br.com.fiap.techchallenge.application.comum.ApplicationException;
import br.com.fiap.techchallenge.application.usuario.dto.CriarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.usuario.dto.EnderecoApp;
import br.com.fiap.techchallenge.application.usuario.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IUsuarioPresenter;
import br.com.fiap.techchallenge.application.usuario.service.CriarUsuarioService;
import br.com.fiap.techchallenge.domain.comum.Endereco;
import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import br.com.fiap.techchallenge.domain.usuario.UsuarioFactoryBuilder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class CriarUsuarioServiceTest {

    @Mock
    IUsuarioRepository repository;
    @Mock
    IUsuarioPresenter presenter;

    @InjectMocks
    CriarUsuarioService service;

    @Test
    @DisplayName("Cria usuário com sucesso")
    void deveCriarUsuarioEChamarPresenterCreated() {
        var req = new CriarUsuarioRequestApp(
                "Patrícia","123.456.789-00", LocalDate.of(1982,5,10),
                "pati@ex.com","11 3456-7890","pati","123456",
                new EnderecoApp("Rua","10","Bairro","Cidade","SP","00000-000",null),
                TipoUsuario.PACIENTE, null, null, null, "Básico"
        );

        var dominioSalvo = UsuarioFactoryBuilder.novo()
                .id(1L).nome(req.nome()).cpf(req.cpf()).dataNascimento(req.dataNascimento())
                .email(req.email()).telefone(req.telefone()).login(req.login()).senha(req.senha())
                .endereco(Endereco.novoEndereco("Rua","10","Bairro","Cidade","SP","00000-000",null))
                .tipoUsuario(req.tipoUsuario()).planoSaude(req.planoSaude()).build();

        when(repository.save(any(Usuario.class))).thenReturn(dominioSalvo);

        service.execute(req);

        verify(repository, times(1)).save(any(Usuario.class));
        verify(presenter, times(1)).created(argThat(resp ->
                resp.id().equals(1L) &&
                        resp.nome().equals("Patrícia") &&
                        resp.tipoUsuario() == TipoUsuario.PACIENTE
        ));
        verifyNoMoreInteractions(repository, presenter);
    }

    @Test
    @DisplayName("Lança exceção caso receba request nulo")
    void deveLancarExceptionQuandoRequestNulo() {
        assertThrows(ApplicationException.class, () -> service.execute(null));
        verifyNoInteractions(repository, presenter);
    }
}
