package br.com.fiap.techchallenge.application;

import br.com.fiap.techchallenge.application.usuario.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IUsuarioPresenter;
import br.com.fiap.techchallenge.application.usuario.service.ListarUsuariosService;
import br.com.fiap.techchallenge.domain.comum.Endereco;
import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import br.com.fiap.techchallenge.domain.usuario.UsuarioFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ListarUsuariosServiceTest {

    @Mock
    IUsuarioRepository repository;
    @Mock
    IUsuarioPresenter presenter;

    @InjectMocks
    ListarUsuariosService service;

    @Test
    void deveListarEChamarPresenterComListaMapeada() {
        var u1 = UsuarioFactoryBuilder.novo()
                .id(1L).nome("A").cpf("111.111.111-11").dataNascimento(LocalDate.now())
                .email("a@ex.com").telefone("00 0000-0000").login("a").senha("123456")
                .endereco(Endereco.novoEndereco("R","1","B","C","SP","00000-000",null))
                .tipoUsuario(TipoUsuario.PACIENTE).planoSaude("Básico").build();

        var u2 = UsuarioFactoryBuilder.novo()
                .id(2L).nome("B").cpf("222.222.222-22").dataNascimento(LocalDate.now())
                .email("b@ex.com").telefone("22 92222-2222").login("b").senha("789456")
                .endereco(Endereco.novoEndereco("R","2","B","C","SP","000",null))
                .tipoUsuario(TipoUsuario.MEDICO).crm("123/SP").especialidade("Neirologista").build();

        when(repository.findAll()).thenReturn(List.of(u1, u2));

        service.execute();

        verify(presenter).presentAll(argThat(lista ->
                lista.itens().size() == 2 &&
                        lista.itens().get(0).id().equals(1L) &&
                        lista.itens().get(1).id().equals(2L)
        ));
        verifyNoMoreInteractions(repository, presenter);
    }

}
