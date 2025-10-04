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
@Table(name = "medicos")
@PrimaryKeyJoinColumn(name = "id")
public class MedicoEntity extends UsuarioEntity {

    @Column(nullable = false, length = 30)
    private String crm;

    @Column(nullable = false, length = 100)
    private String especialidade;

    public MedicoEntity(Long id, String nome, String cpf, java.time.LocalDate dataNasc,
                        String email, String telefone, String senha,
                        EnderecoEmbeddable endereco, String crm, String especialidade) {
        super(id, nome, cpf, dataNasc, email, telefone, senha, TipoUsuario.MEDICO, endereco);
        this.crm = crm;
        this.especialidade = especialidade;
    }

}
