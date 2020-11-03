package br.com.zup.cartaoproposta.entities.cartao;

import java.math.BigDecimal;

/**
 * Contagem de carga intrínseca da classe: 0
 */

public class ParcelaCartaoRetorno {
    private String id;
    private int quantidade;
    private BigDecimal valor;

    @Deprecated
    public ParcelaCartaoRetorno(){}

    public ParcelaCartaoRetorno(String id, int quantidade, BigDecimal valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
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
}
