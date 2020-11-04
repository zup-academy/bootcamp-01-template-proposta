package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Renegociacao {

    @JsonProperty(value = "id")
    private String idRenegociacao;
    private Integer quantidade;
    private BigDecimal valor;
    private String dataDeCriacaoRenegociacao;

    @Deprecated
    public Renegociacao() {
    }

    public Renegociacao(String idRenegociacao, Integer quantidade, BigDecimal valor, String dataDeCriacaoRenegociacao) {
        this.idRenegociacao = idRenegociacao;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacaoRenegociacao = dataDeCriacaoRenegociacao;
    }

    public String getIdRenegociacao() {
        return idRenegociacao;
    }

    public void setIdRenegociacao(String idRenegociacao) {
        this.idRenegociacao = idRenegociacao;
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

    public String getDataDeCriacaoRenegociacao() {
        return dataDeCriacaoRenegociacao;
    }

    public void setDataDeCriacaoRenegociacao(String dataDeCriacaoRenegociacao) {
        this.dataDeCriacaoRenegociacao = dataDeCriacaoRenegociacao;
    }
}
