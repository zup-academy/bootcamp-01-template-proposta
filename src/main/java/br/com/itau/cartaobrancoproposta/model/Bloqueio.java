package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;

@Embeddable
public class Bloqueio {

    @JsonProperty(value = "id")
    private String idBloqueio;
    private String bloqueadoEm;
    private String sistemaResponsavel;
    private Boolean ativo;

    public Bloqueio(String idBloqueio, String bloqueadoEm, String sistemaResponsavel, Boolean ativo) {
        this.idBloqueio = idBloqueio;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
    }

    public String getIdBloqueio() {
        return idBloqueio;
    }

    public void setIdBloqueio(String idBloqueio) {
        this.idBloqueio = idBloqueio;
    }

    public String getBloqueadoEm() {
        return bloqueadoEm;
    }

    public void setBloqueadoEm(String bloqueadoEm) {
        this.bloqueadoEm = bloqueadoEm;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    public void setSistemaResponsavel(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
