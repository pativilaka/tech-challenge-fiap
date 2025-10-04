package br.com.application.autenticacao;

import br.com.fiap.techchallenge.application.usuario.dto.EnderecoApp;
import br.com.fiap.techchallenge.application.usuario.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.application.usuario.mappers.UsuarioMapper;
import br.com.fiap.techchallenge.application.usuario.ports.out.IUsuarioRepository;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IBuscarUsuarioPresenter;
import br.com.fiap.techchallenge.application.usuario.service.BuscarUsuarioService;
import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import br.com.fiap.techchallenge.domain.usuario.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.security.auth.message.AuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarUsuarioServiceTest {

    @Mock
    private IBuscarUsuarioPresenter presenter;

    @Mock
    private IUsuarioRepository repository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private BuscarUsuarioService service;

    private final String jwtSecret = "mySecretKey123456789012345678901234";

    private String validToken;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(service, "jwtSecret", jwtSecret);
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        validToken = Jwts.builder()
                .subject("user@test.com")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Test
    void deveValidarTokenCorretamente() {
        assertTrue(service.validateJwtToken(validToken));
    }

    @Test
    void deveExtrairUsernameDoToken() {
        String username = service.getEmailFromJwtToken(validToken);
        assertEquals("user@test.com", username);
    }

    @Test
    void deveExecutarComTokenValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.alterarEmail("user@test.com");

        when(repository.findByEmail("user@test.com")).thenReturn(Optional.of(usuario));

        try (MockedStatic<UsuarioMapper> mockedMapper = Mockito.mockStatic(UsuarioMapper.class)) {
            UsuarioResponseApp usuarioResponse = new UsuarioResponseApp(
                    1L,
                    "Usuário Teste",
                    "12345678901",
                    LocalDate.of(1990, 1, 1),
                    "user@test.com",
                    "11999999999",
                    new EnderecoApp("Rua Teste", "123", "Teste","SP", "SP", "01000-000", "casa3"),
                    TipoUsuario.PACIENTE,
                    null,
                    null,
                    null,
                    "Plano Fake"
            );

            mockedMapper.when(() -> UsuarioMapper.toApp(any(Usuario.class)))
                    .thenReturn(usuarioResponse);

            service.execute(validToken);

            verify(repository, times(1)).findByEmail("user@test.com");
            verify(presenter, times(1)).present(any(UsuarioResponseApp.class));
        }
    }

    @Test
    void deveLancarAuthExceptionQuandoTokenNulo() {
        assertThrows(AuthException.class, () -> service.execute(null));
    }

    @Test
    void deveLancarAuthExceptionQuandoTokenInvalido() {
        String invalidToken = "invalid.jwt.token";

        assertThrows(AuthException.class, () -> service.execute(invalidToken));
    }

    @Test
    void deveLancarUsernameNotFoundQuandoUsuarioNaoExiste() {
        when(repository.findByEmail("user@test.com")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.execute(validToken));
    }

    @Test
    void deveFalharValidacaoComTokenExpirado() {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        String expiredToken = Jwts.builder()
                .subject("user@test.com")
                .issuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 5))
                .expiration(new Date(System.currentTimeMillis() - 1000 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        assertFalse(service.validateJwtToken(expiredToken));
    }
}