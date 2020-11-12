package br.com.zup.bootcamp.proposta.domain.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
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

    @Future
    private LocalDate validoAte;

    @NotBlank
    private String destino;

    @NotBlank
    private String ip;

    @NotBlank
    private String sistemaResponsavel;

    @CreationTimestamp
    private LocalDateTime solicitadoEm;

    @ManyToOne
    @Valid @NotNull
    private Cartao cartao;

    @Deprecated
    public Aviso(){}

    public Aviso(@NotBlank LocalDate validoAte, @NotBlank String destino, @NotBlank String ip,
                 @NotBlank String sistemaResponsavel) {
        this.validoAte = validoAte;
        this.destino = destino;
        this.ip = ip;
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public Aviso(@NotBlank LocalDate validoAte, @NotBlank String destino, @NotBlank String ip,
                 @NotBlank String sistemaResponsavel, @Valid @NotNull Cartao cartao) {
        this.validoAte = validoAte;
        this.destino = destino;
        this.ip = ip;
        this.sistemaResponsavel = sistemaResponsavel;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(LocalDate validoAte) {
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
