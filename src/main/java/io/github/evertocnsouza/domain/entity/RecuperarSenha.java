package io.github.evertocnsouza.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class RecuperarSenha {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private LocalDateTime recuperadoEm = LocalDateTime.now();

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public RecuperarSenha(){}

    public RecuperarSenha(String ip, String userAgent, Cartao cartao) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }
    public UUID getId() {
        return id;
    }
}