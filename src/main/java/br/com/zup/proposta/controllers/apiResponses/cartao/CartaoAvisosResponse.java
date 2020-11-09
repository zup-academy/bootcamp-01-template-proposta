package br.com.zup.proposta.controllers.apiResponses.cartao;

import java.time.LocalDate;

import br.com.zup.proposta.model.cartao.Cartao;
import br.com.zup.proposta.model.cartao.CartaoAvisos;

public class CartaoAvisosResponse {
    
    private LocalDate validoAte;
    private String destino;

    public CartaoAvisosResponse(LocalDate validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public LocalDate getValidoAte() {
        return this.validoAte;
    }

    public String getDestino() {
        return this.destino;
    }

    public CartaoAvisos toCartaoAviso(Cartao cartao) {
        return new CartaoAvisos(validoAte, destino, "", "", cartao);
    }
}
