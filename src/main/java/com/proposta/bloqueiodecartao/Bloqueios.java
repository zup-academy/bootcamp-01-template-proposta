package com.proposta.bloqueiodecartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Bloqueios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime bloqueadoEm = LocalDateTime.now();

    private Boolean ativo = true;

    @NotBlank
    private String userAgent;

    @NotBlank
    private String ip;

    @Deprecated
    public Bloqueios() {

    }

    public Bloqueios(@NotBlank String userAgent, @NotBlank String ip) {
        this.userAgent = userAgent;
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }
}
