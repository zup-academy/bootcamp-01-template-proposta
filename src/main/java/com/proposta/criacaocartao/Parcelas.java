package com.proposta.criacaocartao;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Parcelas {

    private String id;

    private int quantidade;

    private BigDecimal valor;

    public Parcelas(String id, int quantidade, BigDecimal valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }
}
