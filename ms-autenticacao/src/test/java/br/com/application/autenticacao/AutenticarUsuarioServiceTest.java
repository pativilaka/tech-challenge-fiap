package br.com.application.autenticacao;

import br.com.fiap.techchallenge.application.usuario.dto.AuthenticationResponse;
import br.com.fiap.techchallenge.application.usuario.dto.LoginRequestApp;
import br.com.fiap.techchallenge.application.usuario.ports.presenters.IAutenticacaoPresenter;
import br.com.fiap.techchallenge.application.usuario.service.AutenticarUsuarioService;
import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import br.com.fiap.techchallenge.domain.usuario.UserDetailsImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Base64;
import java.util.Collections;
import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutenticarUsuarioServiceTest {

    @Mock
    private IAutenticacaoPresenter presenter;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    private AutenticarUsuarioService autenticarUsuarioService;
    private LoginRequestApp loginRequest;
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        SecretKey validKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);
        String validBase64Secret = Base64.getEncoder().encodeToString(validKey.getEncoded());

        autenticarUsuarioService = new AutenticarUsuarioService(presenter, authenticationManager);
        autenticarUsuarioService.jwtSecret = validBase64Secret;
        autenticarUsuarioService.jwtExpirationMs = 86400000;

        loginRequest = new LoginRequestApp("usuario@email.com", "senha123");

        userDetails = new UserDetailsImpl(
                1L,
                "usuario123",
                "usuario@email.com",
                "senha123",
                TipoUsuario.PACIENTE,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"))
        );
    }

    @Test
    void deveAutenticarUsuarioComSucesso() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Act
        autenticarUsuarioService.execute(loginRequest);

        // Assert
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // O getPrincipal() é chamado 2 vezes: uma no execute() e outra no generateJwtToken()
        verify(authentication, times(2)).getPrincipal();

        verify(presenter).present(any(AuthenticationResponse.class));

        // Verifica se o contexto de segurança foi atualizado
        Authentication contextAuth = SecurityContextHolder.getContext().getAuthentication();
        assertEquals(authentication, contextAuth);
    }

    @Test
    void deveLancarExcecaoQuandoCredenciaisForemInvalidas() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            autenticarUsuarioService.execute(loginRequest);
        });

        verify(presenter, never()).present(any(AuthenticationResponse.class));
        verify(authentication, never()).getPrincipal();
    }

    @Test
    void deveChamarPresenterComDadosCorretos() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Act
        autenticarUsuarioService.execute(loginRequest);

        // Assert
        verify(presenter).present(argThat(response ->
                response.id().equals(1L) &&
                        response.username().equals("usuario123") &&
                        response.email().equals("usuario@email.com") &&
                        response.tipoUsuario().equals(TipoUsuario.PACIENTE) &&
                        response.token() != null &&
                        !response.token().isEmpty()
        ));
    }

    @Test
    void deveGerarTokenJwtValido() {
        // Arrange
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Act
        String jwtToken = autenticarUsuarioService.generateJwtToken(authentication);

        // Assert
        assertNotNull(jwtToken);
        assertFalse(jwtToken.isEmpty());

        // Verifica se o token tem a estrutura básica JWT (3 partes separadas por ponto)
        String[] tokenParts = jwtToken.split("\\.");
        assertEquals(3, tokenParts.length, "O token JWT deve ter 3 partes");
    }

    @Test
    void deveLimparContextoSegurancaAposExecucao() {
        // Arrange - Configura um contexto sujo primeiro
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Act
        autenticarUsuarioService.execute(loginRequest);

        // Assert - O contexto deve ter sido atualizado com a nova autenticação
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        // Cleanup
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }
}