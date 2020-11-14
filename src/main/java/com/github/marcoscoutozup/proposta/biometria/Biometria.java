package com.github.marcoscoutozup.proposta.biometria;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    private byte[] fingerprint;

    @CreationTimestamp
    private LocalDateTime created_at;

    @Deprecated
    public Biometria() {
    }

    public Biometria(@NotNull String fingerprint) {
        this.fingerprint = fingerprint.getBytes();
    }

    public UUID getId() {
        return id;
    }

}
