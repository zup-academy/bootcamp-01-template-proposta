package br.com.zup.bootcamp.proposta.domain.entity;

import br.com.zup.bootcamp.proposta.api.handler.Base64;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull @Base64
    private byte[] fingerprint;

    @CreationTimestamp
    private LocalDateTime criadoEm;

    @Valid
    @ManyToOne @NotNull
    private Cartao cartao;

    @Deprecated
    public Biometria() {
    }

    public Biometria(@NotNull byte[] fingerprint, @Valid @NotNull Cartao cartao) {
        this.fingerprint = fingerprint;
        this.cartao = cartao;
    }

    public Biometria(@NotNull byte[] fingerprint) {
        this.fingerprint = fingerprint;
    }

    public byte[] getFingerprint() {
        return fingerprint;
    }

}
