package br.com.zup.proposta.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    private String fingerprint;

    @CreationTimestamp
    private LocalDateTime criadoEm;

    @Deprecated
    public Biometria() {
    }

    public Biometria(@NotNull String fingerprint) {
        this.fingerprint = Base64.getEncoder().encodeToString(fingerprint.getBytes());
    }

    public UUID getId() {
        return id;
    }
}
