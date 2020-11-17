package io.github.evertocnsouza.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate validoAte;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @PastOrPresent
    private LocalDateTime avisadoEm = LocalDateTime.now();

    @Deprecated
    public AvisoViagem() {
    }

    public AvisoViagem(@NotBlank String destino, @NotNull @Future LocalDate validoAte,
                       @NotBlank String ip, @NotBlank String userAgent) {
        this.destino = destino;
        this.validoAte = validoAte;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(LocalDate validoAte) {
        this.validoAte = validoAte;
    }

}
