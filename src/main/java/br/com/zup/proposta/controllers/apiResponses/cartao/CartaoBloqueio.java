package br.com.zup.proposta.controllers.apiResponses.cartao;

import java.time.LocalDateTime;

public class CartaoBloqueio {
    
    private String id;
    private LocalDateTime bloqueadoEm;
    private String sistemaResponsavel;
    private boolean ativo;

    public CartaoBloqueio(String id, LocalDateTime bloqueadoEm, String sistemaResponsavel, boolean ativo) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
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
