package br.com.fiap.techchallenge.application.mappers;

import br.com.fiap.techchallenge.application.usuario.mappers.EnderecoMapper;
import br.com.fiap.techchallenge.domain.comum.Endereco;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class EnderecoMapperTest {

    @Test
    void voToAppAndBack() {
        var vo = Endereco.novoEndereco("R","1","B","C","SP","00000-000",null);
        var app = EnderecoMapper.toApp(vo);
        assertEquals("R", app.logradouro());
        var vo2 = EnderecoMapper.toDomain(app);
        assertEquals(vo, vo2);
    }
}
