package com.proposta.cadastraravisoviagem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataAviso = LocalDateTime.now();

    @NotBlank
    private String userAgent;

    @NotBlank
    private String ip;

    @NotBlank
    private String destino;

    @Future
    @NotNull
    private LocalDate validoAte;

    @Deprecated
    public AvisoViagem() {

    }

    public AvisoViagem(@NotBlank String userAgent, @NotBlank String ip,
                       @NotBlank String destino, @Future @NotNull LocalDate validoAte) {
        this.userAgent = userAgent;
        this.ip = ip;
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public Long getId() {
        return id;
    }
}
