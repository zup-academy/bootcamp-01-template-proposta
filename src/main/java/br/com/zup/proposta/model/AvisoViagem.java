package br.com.zup.proposta.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotBlank
    private String destinoViagem;

    @NotNull @Future
    private LocalDate validoAte;

    @CreationTimestamp
    private LocalDateTime instanteSolicitacao;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @Deprecated
    public AvisoViagem() {
    }

    public AvisoViagem(@NotBlank String destinoViagem, @NotNull @Future LocalDate validoAte, @NotBlank String ip, @NotBlank String userAgent) {
        this.destinoViagem = destinoViagem;
        this.validoAte = validoAte;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public UUID getId() {
        return id;
    }

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public LocalDateTime getInstanteSolicitacao() {
        return instanteSolicitacao;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
