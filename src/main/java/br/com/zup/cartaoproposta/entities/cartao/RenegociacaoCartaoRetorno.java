package br.com.zup.cartaoproposta.entities.cartao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Contagem de carga intrínseca da classe: 0
 */

public class RenegociacaoCartaoRetorno {
    private String id;
    private int quantidade;
    private BigDecimal valor;
    private LocalDateTime dataDeCriacao;

    @Deprecated
    public RenegociacaoCartaoRetorno(){}

    public RenegociacaoCartaoRetorno(String id, int quantidade, BigDecimal valor, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public RenegociacaoCartao toModel() {
        return new RenegociacaoCartao(id, quantidade, valor, dataDeCriacao);
    }
}
