package br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity;

import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "pacientes")
@PrimaryKeyJoinColumn(name = "id")
public class PacienteEntity extends UsuarioEntity{

    @Column(name = "plano_saude", nullable = false, length = 50)
    private String planoSaude;


    public PacienteEntity(Long id, String nome, String cpf, java.time.LocalDate dataNasc,
                          String email, String telefone, String senha,
                          EnderecoEmbeddable endereco, String planoSaude) {
        super(id, nome, cpf, dataNasc, email, telefone, senha, TipoUsuario.PACIENTE, endereco);
        this.planoSaude = planoSaude;
    }
}
