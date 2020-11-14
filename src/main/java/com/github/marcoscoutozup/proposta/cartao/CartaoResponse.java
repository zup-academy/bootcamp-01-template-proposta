package com.github.marcoscoutozup.proposta.cartao;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CartaoResponse {

    @NotNull
    private String id;

    @NotNull
    private LocalDateTime emitidoEm;

            //1
    public Cartao toCartao(){
        return new Cartao(id, emitidoEm);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public void setEmitidoEm(LocalDateTime emitidoEm) {
        this.emitidoEm = emitidoEm;
    }

}
