package br.com.zup.proposta.bloqueio;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private @NotBlank String ip;
    private @NotBlank String userAgent;
    private LocalDateTime instante = LocalDateTime.now();

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(@NotBlank String ip, @NotBlank String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getId() {
        return id;
    }
}
