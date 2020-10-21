package com.github.marcoscoutozup.proposta.cartao.parcela;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Parcela {

    private Integer quantidade;
    private BigDecimal valor;

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Parcela{" +
                "quantidade=" + quantidade +
                ", valor=" + valor +
                '}';
    }
}
