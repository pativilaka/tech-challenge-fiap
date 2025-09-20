package br.com.fiap.techchallenge.application.mappers;

import br.com.fiap.techchallenge.application.dto.AtualizarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.dto.CriarUsuarioRequestApp;
import br.com.fiap.techchallenge.application.dto.EnderecoApp;
import br.com.fiap.techchallenge.application.dto.UsuarioResponseApp;
import br.com.fiap.techchallenge.domain.comum.Endereco;
import br.com.fiap.techchallenge.domain.usuario.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UsuarioMapperTest {

    private Endereco voEndereco() {
        return Endereco.novoEndereco("Rua A", "123", "Centro", "São Paulo", "SP", "00000-000", null);
    }

    private EnderecoApp dtoEndereco() {
        return new EnderecoApp("Rua A", "123", "Centro", "São Paulo", "SP", "00000-000", null);
    }

    @Test
    void toApp_deveMapearMedico_comCamposEspecificos() {
        Usuario u = UsuarioFactoryBuilder.novo()
                .id(1L)
                .nome("Dra. Ana")
                .cpf("000.000.000-00")
                .dataNascimento(LocalDate.of(1985, 1, 1))
                .email("ana@ex.com")
                .telefone("11 99999-9999")
                .senha("segura1")
                .endereco(voEndereco())
                .tipoUsuario(TipoUsuario.MEDICO)
                .crm("CRM-123")
                .especialidade("Clínica")
                .build();

        assertTrue(u instanceof Medico);

        UsuarioResponseApp resp = UsuarioMapper.toApp(u);

        assertNotNull(resp);
        assertEquals(1L, resp.id());
        assertEquals("Dra. Ana", resp.nome());
        assertEquals(TipoUsuario.MEDICO, resp.tipoUsuario());
        assertEquals("CRM-123", resp.crm());
        assertEquals("Clínica", resp.especialidade());
        assertNull(resp.coren());
        assertNull(resp.planoSaude());
        assertNotNull(resp.endereco());
        assertEquals("Rua A", resp.endereco().logradouro());
    }

    @Test
    void toApp_deveMapearEnfermeira_comCoren() {
        Usuario u = UsuarioFactoryBuilder.novo()
                .id(2L)
                .nome("Enfa. Bia")
                .cpf("111.111.111-11")
                .dataNascimento(LocalDate.of(1990,2,2))
                .email("bia@ex.com")
                .telefone("11 99999-9999")
                .senha("segura1")
                .tipoUsuario(TipoUsuario.ENFERMEIRO)
                .coren("COREN-9")
                .build();

        assertTrue(u instanceof Enfermeiro);

        var resp = UsuarioMapper.toApp(u);
        assertNotNull(resp);
        assertEquals(TipoUsuario.ENFERMEIRO, resp.tipoUsuario());
        assertEquals("COREN-9", resp.coren());
        assertNull(resp.crm());
        assertNull(resp.especialidade());
        assertNull(resp.planoSaude());
    }

    @Test
    void toApp_deveMapearPaciente_comPlanoSaude() {
        Usuario u = UsuarioFactoryBuilder.novo()
                .id(3L)
                .nome("João")
                .cpf("222.222.222-22")
                .dataNascimento(LocalDate.of(1995,3,3))
                .email("joao@ex.com")
                .telefone("11 99999-9999")
                .senha("segura1")
                .tipoUsuario(TipoUsuario.PACIENTE)
                .planoSaude("Plano X")
                .build();

        assertTrue(u instanceof Paciente);

        var resp = UsuarioMapper.toApp(u);
        assertEquals(TipoUsuario.PACIENTE, resp.tipoUsuario());
        assertEquals("Plano X", resp.planoSaude());
        assertNull(resp.crm());
        assertNull(resp.especialidade());
        assertNull(resp.coren());
    }

    @Test
    void toDomain_deveCriarMedico_comCrmEEspecialidade() {
        CriarUsuarioRequestApp req = new CriarUsuarioRequestApp(
                "Dra. Ana",
                "000.000.000-00",
                LocalDate.of(1985,1,1),
                "ana@ex.com",
                "11 99999-9999",
                "segura1",
                "123456",
                dtoEndereco(),
                TipoUsuario.MEDICO,
                "CRM-123",
                "Clínica",
                null,
                null
        );

        Usuario u = UsuarioMapper.toDomain(req);
        assertNotNull(u);
        assertTrue(u instanceof Medico);
        assertEquals(TipoUsuario.MEDICO, u.getTipoUsuario());

        Medico m = (Medico) u;
        assertEquals("CRM-123", m.getCrm());
        assertEquals("Clínica", m.getEspecialidade());
    }

    @Test
    void toDomain_deveCriarEnfermeira_comCoren() {
        CriarUsuarioRequestApp reqEnfermeira = new CriarUsuarioRequestApp(
                "Bia",
                "111.111.111-11",
                LocalDate.of(1990,2,2),
                "bia@ex.com",
                "11 99999-9999",
                null,
                "456123",
                null,
                TipoUsuario.ENFERMEIRO,
                null,
                null, "COREN-9",
                null
        );

        Usuario u = UsuarioMapper.toDomain(reqEnfermeira);
        assertTrue(u instanceof Enfermeiro);
        assertEquals(TipoUsuario.ENFERMEIRO, u.getTipoUsuario());
    }

    @Test
    void toDomain_deveCriarPaciente_comPlanoSaude() {
        CriarUsuarioRequestApp req = new CriarUsuarioRequestApp(
                "João",
                "222.222.222-22",
                LocalDate.of(1995,3,3),
                "joao@ex.com",
                "11 99999-9999",
                "segura1",
                "789456",
                null,
                TipoUsuario.PACIENTE,
                null,
                null,
                null,
                "Plano X"

        );

        Usuario u = UsuarioMapper.toDomain(req);
        assertTrue(u instanceof Paciente);
        assertEquals(TipoUsuario.PACIENTE, u.getTipoUsuario());
    }

    @Test
    void toApp_toDomain_devemRetornarNullQuandoEntradaForNull_e_toUpdateDomainNaoAlteraQuandoRequestNull() {
        assertNull(UsuarioMapper.toApp(null));
        assertNull(UsuarioMapper.toDomain(null));

        var existente = UsuarioFactoryBuilder.novo()
                .id(20L)
                .nome("Fulano")
                .cpf("000.000.000-00")
                .dataNascimento(LocalDate.of(1990,1,1))
                .email("fulano@ex.com")
                .telefone("11 99999-9999")
                .senha("segura1")
                .tipoUsuario(TipoUsuario.PACIENTE)
                .planoSaude("Plano Z")
                .endereco(voEndereco())
                .build();

        var nomeAntes = existente.getNome();
        var emailAntes = existente.getEmail();
        var telAntes = existente.getTelefone();
        var senhaAntes = existente.getSenha();
        var planoAntes = (existente instanceof Paciente p) ? p.getPlanoSaude() : null;
        var endAntes = existente.getEndereco();

        assertDoesNotThrow(() -> UsuarioMapper.toUpdateDomain(null, existente));

        assertEquals(nomeAntes, existente.getNome());
        assertEquals(emailAntes, existente.getEmail());
        assertEquals(telAntes, existente.getTelefone());
        assertEquals(senhaAntes, existente.getSenha());
        assertEquals(planoAntes, (existente instanceof Paciente p) ? p.getPlanoSaude() : null);
        assertSame(endAntes, existente.getEndereco());

        assertDoesNotThrow(() -> UsuarioMapper.toUpdateDomain(null, null));

        var reqErro = new AtualizarUsuarioRequestApp(null,null,null,null,null,null,null,null,null, null,null, null, null, null);
        assertDoesNotThrow(() -> UsuarioMapper.toUpdateDomain(reqErro, null));
    }


}