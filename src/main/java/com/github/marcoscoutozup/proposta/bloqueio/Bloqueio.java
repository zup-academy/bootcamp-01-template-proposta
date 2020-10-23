package com.github.marcoscoutozup.proposta.bloqueio;

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
    private LocalDateTime instante;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(@NotBlank String ip, @NotBlank String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return "Bloqueio{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
