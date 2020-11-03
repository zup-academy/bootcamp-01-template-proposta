package br.com.zup.proposta.controllers.apiResponses.cartao;

import java.time.LocalDateTime;

import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.model.cartao.CartaoRenegociacao;

public class CartaoRenegociacaoResponse {
    
    private String id;
    private Number quantidade;
    private Number valor;
    private LocalDateTime dataDeCriacao;

    public CartaoRenegociacaoResponse(String id, Number quantidade, Number valor, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return this.id;
    }

    public Number getQuantidade() {
        return this.quantidade;
    }

    public Number getValor() {
        return this.valor;
    }

    public LocalDateTime getDataDeCriacao() {
        return this.dataDeCriacao;
    }

    public CartaoRenegociacao toRenegociacao(Cartao cartao) {
        return new CartaoRenegociacao(this.id, this.quantidade, this.valor, this.dataDeCriacao, cartao);
    }
}
