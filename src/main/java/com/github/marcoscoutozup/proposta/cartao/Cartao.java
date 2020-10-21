package com.github.marcoscoutozup.proposta.cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    private UUID id;

    @NotNull
    private LocalDateTime emitidoEm;

    @Deprecated
    public Cartao() {
    }

    public Cartao(UUID id, LocalDateTime emitidoEm) {
        this.id = id;
        this.emitidoEm = emitidoEm;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "id=" + id +
                ", emitidoEm=" + emitidoEm +
                '}';
    }
}
