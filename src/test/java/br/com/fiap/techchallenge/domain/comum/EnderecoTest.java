package br.com.fiap.techchallenge.domain.comum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnderecoTest {

    @Test
    @DisplayName("Deve criar Endereco válido")
    void endereco_criacao_sucesso() {

        var e = Endereco.novoEdereco(
                "Rua A", "123", "Bairro B", "São Paulo", "SP", "01234-567", "Apto 1"
        );

        assertNotNull(e);
        assertEquals("Rua A", e.getLogradouro());
        assertTrue(e.toString().contains("logradouro='Rua A'"));
    }

    @Test
    @DisplayName("Deve validar UF com tamanho mínimo e CEP não em branco")
    void endereco_validacao_invalida() {

        var ex1 = assertThrows(DomainException.class, () ->
                Endereco.novoEdereco("Rua", "1", "B", "SP", "S", "01234-567", null)
        );
        assertTrue(ex1.getMessage().contains("uf"));

        var ex2 = assertThrows(DomainException.class, () ->
                Endereco.novoEdereco("Rua", "1", "B", "SP", "SP", "", null)
        );
        assertTrue(ex2.getMessage().contains("cep"));
    }

    @Test
    @DisplayName("Equals/HashCode devem considerar todos os campos (VO)")
    void equals_hashCode_vo() {
        var e1 = Endereco.novoEdereco("Rua", "1", "Bairro", "Cidade", "SP", "00000-000", null);
        var e2 = Endereco.novoEdereco("Rua", "1", "Bairro", "Cidade", "SP", "00000-000", null);

        boolean iguais = e1.equals(e2);

        assertTrue(iguais);
        assertEquals(e1.hashCode(), e2.hashCode());
    }
}
