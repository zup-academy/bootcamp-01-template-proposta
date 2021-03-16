package br.com.zup.proposta.aviso;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Aviso {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private @NotBlank String destino;
    private @NotNull @Future LocalDate dataTermino;
    private @NotBlank String ip;
    private @NotBlank String userAgent;
    private LocalDateTime instante = LocalDateTime.now();

    @Deprecated
    public Aviso() {
    }

    public Aviso(@NotBlank String destino, @NotNull @Future LocalDate dataTermino, @NotBlank String ip, @NotBlank String userAgent) {
        this.destino = destino;
        this.dataTermino = dataTermino;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getId() {
        return id;
    }
}
