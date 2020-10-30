package io.github.evertocnsouza.domain.entity;

import io.github.evertocnsouza.domain.enums.StatusCartao;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private UUID numeroCartao;

    @NotNull
    private LocalDateTime emitidoEm;

    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;

    @Deprecated
    public Cartao(){
    }
    public Cartao(UUID numeroCartao, LocalDateTime emitidoEm) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.statusCartao = statusCartao.ATIVO;
    }
}