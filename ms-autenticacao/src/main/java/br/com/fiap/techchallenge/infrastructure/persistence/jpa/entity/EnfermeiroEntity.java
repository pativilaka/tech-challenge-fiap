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
@Table(name = "enfermeiros")
@PrimaryKeyJoinColumn(name = "id")
public class EnfermeiroEntity extends UsuarioEntity{

    @Column(nullable = false, length = 30)
    private String coren;

    public EnfermeiroEntity(Long id, String nome, String cpf, java.time.LocalDate dataNasc,
                            String email, String telefone, String senha,
                            EnderecoEmbeddable endereco, String coren) {
        super(id, nome, cpf, dataNasc, email, telefone, senha, TipoUsuario.ENFERMEIRO, endereco);
        this.coren = coren;
    }

}
