package br.com.zup.proposta.controllers.apiResponses.cartao;

import java.time.LocalDateTime;

import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.model.cartao.CartaoBloqueio;

public class CartaoBloqueioResponse {
    
    private String id;
    private LocalDateTime bloqueadoEm;
    private String sistemaResponsavel;
    private boolean ativo;

    public CartaoBloqueioResponse(String id, LocalDateTime bloqueadoEm, String sistemaResponsavel, boolean ativo) {
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

    public CartaoBloqueio toCartaoBloqueio(Cartao cartao) {
        return new CartaoBloqueio(this, cartao);
    }
}
