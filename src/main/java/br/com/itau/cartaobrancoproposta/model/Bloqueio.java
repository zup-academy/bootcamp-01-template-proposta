package br.com.itau.cartaobrancoproposta.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String idBloqueio;
    private String bloqueadoEm;
    private String sistemaResponsavel;
    private Boolean ativo;
    @ManyToOne
    private Cartao cartao;

    public Bloqueio(String idBloqueio, String bloqueadoEm, String sistemaResponsavel, Boolean ativo) {
        this.idBloqueio = idBloqueio;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
