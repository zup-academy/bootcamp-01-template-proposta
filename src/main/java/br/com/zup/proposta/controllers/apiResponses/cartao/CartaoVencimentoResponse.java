package br.com.zup.proposta.controllers.apiResponses.cartao;

import java.time.LocalDateTime;

import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.model.cartao.CartaoVencimento;

public class CartaoVencimentoResponse {
    
    private String id;
    private Integer dia;
    private LocalDateTime dataDeCriacao;

    public CartaoVencimentoResponse(String id, Integer dia, LocalDateTime dataDeCriacao) {
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

    public CartaoVencimento toVencimento(Cartao cartao) {
        return new CartaoVencimento(this.id, this.dia, this.dataDeCriacao, cartao);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", dia='" + getDia() + "'" +
            ", dataDeCriacao='" + getDataDeCriacao() + "'" +
            "}";
    }

}
