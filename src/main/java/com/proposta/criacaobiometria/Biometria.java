package com.proposta.criacaobiometria;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String idCartao;

    @NotBlank
    private String fingerPrint;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    public Biometria(@NotNull String idCartao, @NotBlank String fingerPrint) {
        this.idCartao = idCartao;
        this.fingerPrint = fingerPrint;
    }

    public Long getId() {
        return id;
    }
}
