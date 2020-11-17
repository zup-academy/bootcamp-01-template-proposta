package br.com.zup.proposta.model.cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.com.zup.proposta.controllers.apiResponses.cartao.ParcelaResponse;

@Entity
public class Parcela {

    @Id
    private String id;
    @NotNull
    private Integer quantidade;
    @NotNull
    private Double valor;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Parcela(){}

    public Parcela(ParcelaResponse response, Cartao cartao) {
        this.id = response.getId();
        this.quantidade = response.getQuantidade();
        this.valor = response.getValor();
        this.cartao = cartao;
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
