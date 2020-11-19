package com.proposta.solicitacaoderecuperacaodesenha;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class RecuperarSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataSolicitacao = LocalDateTime.now();

    @NotBlank
    private String userAgent;

    @NotBlank
    private String ip;

    @Deprecated
    public RecuperarSenha() {

    }

    public RecuperarSenha(@NotBlank String userAgent, @NotBlank String ip) {
        this.userAgent = userAgent;
        this.ip = ip;
    }

    public Long getId() {
        return id;
    }
}
