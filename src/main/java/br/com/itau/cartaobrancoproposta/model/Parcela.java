package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Parcela {

    @JsonProperty(value = "id")
    private String idParcela;
    private Integer quantidade;
    private BigDecimal valor;

    public Parcela(String idParcela, Integer quantidade, BigDecimal valor) {
        this.idParcela = idParcela;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getIdParcela() {
        return idParcela;
    }

    public void setIdParcela(String idParcela) {
        this.idParcela = idParcela;
    }

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
}
