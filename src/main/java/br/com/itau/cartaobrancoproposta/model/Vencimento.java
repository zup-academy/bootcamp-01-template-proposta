package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

@Embeddable
public class Vencimento {

    @JsonProperty("id")
    private String idVencimento;
    private Integer dia;
    private String dataDeCriacao;

    @Deprecated
    public Vencimento() {
    }

    public Vencimento(String idVencimento, Integer dia, String dataDeCriacao) {
        this.idVencimento = idVencimento;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getIdVencimento() {
        return idVencimento;
    }

    public void setIdVencimento(String idVencimento) {
        this.idVencimento = idVencimento;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public String getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(String dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }
}
