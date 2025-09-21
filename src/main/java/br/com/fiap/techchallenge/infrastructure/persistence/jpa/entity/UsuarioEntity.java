package br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity;

import br.com.fiap.techchallenge.domain.usuario.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
public abstract class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 55)
    private String nome;

    @Column(nullable = false, length = 14)
    private String cpf;

    @Column(name = "data_de_nascimento", nullable = false)
    private java.time.LocalDate dataNascimento;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(nullable = false, length = 6)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false, length = 15)
    private TipoUsuario tipoUsuario;

    @Embedded
    private EnderecoEmbeddable endereco;

    public UsuarioEntity(Long id, String nome, String cpf, java.time.LocalDate dataNascimento,
                         String email, String telefone, String senha,
                         TipoUsuario tipoUsuario, EnderecoEmbeddable endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.endereco = endereco;
    }
}
