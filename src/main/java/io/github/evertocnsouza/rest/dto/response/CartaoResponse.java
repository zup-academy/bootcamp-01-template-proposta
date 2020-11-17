package io.github.evertocnsouza.rest.dto.response;

import io.github.evertocnsouza.domain.entity.Cartao;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class CartaoResponse {

    @NotNull
    private UUID id;

    @NotNull
    private LocalDateTime emitidoEm;

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
