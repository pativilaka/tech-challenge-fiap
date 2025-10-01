package br.com.fiap.techchallenge.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class EnderecoEmbeddable {
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String complemento;

    public EnderecoEmbeddable(String logradouro, String numero, String bairro,
                              String cidade, String uf, String cep, String complemento) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.complemento = complemento;
    }
}
