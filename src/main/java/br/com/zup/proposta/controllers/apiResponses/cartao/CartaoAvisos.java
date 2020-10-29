package br.com.zup.proposta.controllers.apiResponses.cartao;

import java.time.LocalDate;

public class CartaoAvisos {
    
    private LocalDate validoAte;
    private String destino;

    public CartaoAvisos(LocalDate validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public LocalDate getValidoAte() {
        return this.validoAte;
    }

    public String getDestino() {
        return this.destino;
    }

}
