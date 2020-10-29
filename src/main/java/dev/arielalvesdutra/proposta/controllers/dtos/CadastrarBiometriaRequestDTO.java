package dev.arielalvesdutra.proposta.controllers.dtos;

import dev.arielalvesdutra.proposta.annotations.Base64;
import dev.arielalvesdutra.proposta.entities.Biometria;
import dev.arielalvesdutra.proposta.entities.Cartao;

import javax.validation.constraints.NotNull;

public class CadastrarBiometriaRequestDTO {

    @Base64
    @NotNull
    private String valor;

    public CadastrarBiometriaRequestDTO() {
    }

    public String getValor() {
        return valor;
    }

    public CadastrarBiometriaRequestDTO setValor(String valor) {
        this.valor = valor;
        return this;
    }

    public Biometria paraEntidade(Cartao cartao) {
        return new Biometria()
                .setCartao(cartao)
                .setValor(valor);
    }
}
