package br.com.zup.proposta.controllers.apiResponses.cartao;

import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.model.cartao.Parcela;

public class ParcelaResponse {
    
    private String id;
    private Integer quantidade;
    private Double valor;

    public ParcelaResponse(String id, Integer quantidade, Double valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getId() {
        return this.id;
    }

    public Integer getQuantidade() {
        return this.quantidade;
    }

    public Double getValor() {
        return this.valor;
    }

    public Parcela toParcela(Cartao cartao) {
        return new Parcela(this, cartao);
    }
}
