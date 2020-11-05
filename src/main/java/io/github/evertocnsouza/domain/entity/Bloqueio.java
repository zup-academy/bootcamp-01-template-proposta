package io.github.evertocnsouza.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime bloqueadoEm = LocalDateTime.now();

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(@NotBlank String ip, @NotBlank String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }
    public Long getId() {
        return id;
    }
}

