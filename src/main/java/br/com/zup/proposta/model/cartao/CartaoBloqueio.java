package br.com.zup.proposta.model.cartao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoBloqueioResponse;

@Entity
public class CartaoBloqueio {
    
    @Id
    private String id;
    @NotNull
    private LocalDateTime bloqueadoEm;
    @NotNull
    private String sistemaResponsavel;
    @NotNull
    private boolean ativo;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public CartaoBloqueio(){}

    public CartaoBloqueio(CartaoBloqueioResponse response, Cartao cartao) {
        this.id = response.getId();
        this.bloqueadoEm = response.getBloqueadoEm();
        this.sistemaResponsavel = response.getSistemaResponsavel();
        this.ativo = response.getAtivo();
        this.cartao = cartao;
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getBloqueadoEm() {
        return this.bloqueadoEm;
    }

    public String getSistemaResponsavel() {
        return this.sistemaResponsavel;
    }

    public boolean getAtivo() {
        return this.ativo;
    }

    public boolean isAtivo() {
        return this.ativo;
    }

}
