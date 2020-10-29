package br.com.zup.proposta.controllers.apiResponses.cartao;

import java.time.LocalDateTime;

public class CartaoRenegociacao {
    
    private String id;
    private Integer quantidade;
    private Double valor;
    private LocalDateTime dataDeCriacao;

    public CartaoRenegociacao(String id, Integer quantidade, Double valor, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
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

    public LocalDateTime getDataDeCriacao() {
        return this.dataDeCriacao;
    }

}
