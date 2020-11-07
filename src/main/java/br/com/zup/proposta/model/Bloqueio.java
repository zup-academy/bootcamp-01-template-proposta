package br.com.zup.proposta.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @CreationTimestamp
    private LocalDateTime instanteBloqueio;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(@NotBlank String ip, @NotBlank String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public UUID getId() {
        return id;
    }
}
