package br.com.fiap.techchallenge.domain.comum;

import java.util.Objects;

public final class Endereco {

    private final String logradouro;
    private final String numero;
    private final String bairro;
    private final String cidade;
    private final String uf;
    private final String cep;
    private final String complemento;

    private Endereco(
            String logradouro,
            String numero,
            String bairro,
            String cidade,
            String uf,
            String cep,
            String complemento
    ) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.complemento = complemento;
        validar();
    }

    public static Endereco novoEndereco(
            String logradouro,
            String numero,
            String bairro,
            String cidade,
            String uf,
            String cep,
            String complemento
    ) {
        return new Endereco(logradouro, numero, bairro, cidade, uf, cep, complemento);
    }

    private void validar() {
        DomainValidation.notBlank(logradouro, "logradouro");
        DomainValidation.notBlank(numero, "numero");
        DomainValidation.notBlank(bairro, "bairro");
        DomainValidation.notBlank(cidade, "cidade");
        DomainValidation.notBlank(uf, "uf");
        DomainValidation.minLength(uf, 2, "uf");
        DomainValidation.notBlank(cep, "cep");
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public String getCep() {
        return cep;
    }

    public String getComplemento() {
        return complemento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(logradouro, endereco.logradouro)
                && Objects.equals(numero, endereco.numero)
                && Objects.equals(bairro, endereco.bairro)
                && Objects.equals(cidade, endereco.cidade)
                && Objects.equals(uf, endereco.uf)
                && Objects.equals(cep, endereco.cep)
                && Objects.equals(complemento, endereco.complemento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logradouro, numero, bairro, cidade, uf, cep, complemento);
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", uf='" + uf + '\'' +
                ", cep='" + cep + '\'' +
                ", complemento='" + complemento + '\'' +
                '}';
    }
}
