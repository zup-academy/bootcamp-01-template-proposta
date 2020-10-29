package br.com.zup.proposta.controllers.apiResponses.cartao;

import java.time.LocalDateTime;

public class CartaoVencimento {
    
    private String id;
    private Integer dia;
    private LocalDateTime dataDeCriacao;

    public CartaoVencimento(String id, Integer dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return this.id;
    }

    public Integer getDia() {
        return this.dia;
    }

    public LocalDateTime getDataDeCriacao() {
        return this.dataDeCriacao;
    }

}
