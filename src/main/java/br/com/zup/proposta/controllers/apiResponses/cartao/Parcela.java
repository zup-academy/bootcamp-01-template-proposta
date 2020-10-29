package br.com.zup.proposta.controllers.apiResponses.cartao;

public class Parcela {
    
    private String id;
    private Integer quantidade;
    private Double valor;

    public Parcela(String id, Integer quantidade, Double valor) {
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

}
