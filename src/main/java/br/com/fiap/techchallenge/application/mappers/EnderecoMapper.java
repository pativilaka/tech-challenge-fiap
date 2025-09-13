package br.com.fiap.techchallenge.application.mappers;

import br.com.fiap.techchallenge.application.dto.EnderecoApp;
import br.com.fiap.techchallenge.domain.comum.Endereco;

public final class EnderecoMapper {

    private EnderecoMapper() {
    }

    public static EnderecoApp toApp(Endereco endereco){
        if (endereco == null) return null;
        return new EnderecoApp(
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getCep(),
                endereco.getComplemento()
        );

    }

    public static Endereco toDomain(EnderecoApp enderecoApp){
        if (enderecoApp == null) return null;
        return Endereco.novoEndereco(
                enderecoApp.logradouro(),
                enderecoApp.numero(),
                enderecoApp.bairro(),
                enderecoApp.cidade(),
                enderecoApp.uf(),
                enderecoApp.cep(),
                enderecoApp.complemento()
        );
    }


}
