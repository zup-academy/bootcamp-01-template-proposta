package com.github.marcoscoutozup.proposta.cartao;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class CartaoResponse {

    @NotNull
    private UUID id;

    @NotNull
    private LocalDateTime emitidoEm;

            //1
    public Cartao toCartao(){
        return new Cartao(id, emitidoEm);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public void setEmitidoEm(LocalDateTime emitidoEm) {
        this.emitidoEm = emitidoEm;
    }

}
