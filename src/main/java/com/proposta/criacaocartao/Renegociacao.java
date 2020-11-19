package com.proposta.criacaocartao;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Embeddable
public class Renegociacao {

    private String id;

    private int quantidade;

    private BigDecimal valor;

    private LocalDateTime dataDeCriacao;

    public Renegociacao(String id, int quantidade, BigDecimal valor, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
    }
}
