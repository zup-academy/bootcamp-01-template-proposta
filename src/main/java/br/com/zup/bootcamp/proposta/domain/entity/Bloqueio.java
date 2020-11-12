package br.com.zup.bootcamp.proposta.domain.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String ip;

    @NotBlank
    private String sistemaResponsavel;

    @CreationTimestamp
    private LocalDateTime bloqueadoEm;

    @ManyToOne
    @Valid @NotNull
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(@NotBlank String ip, @NotBlank String sistemaResponsavel, Cartao cartao) {
        this.ip = ip;
        this.sistemaResponsavel = sistemaResponsavel;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

}
