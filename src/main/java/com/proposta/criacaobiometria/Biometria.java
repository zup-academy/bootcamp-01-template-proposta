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

    @NotBlank
    private String fingerPrint;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    public Biometria(@NotBlank String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public Long getId() {
        return id;
    }
}
