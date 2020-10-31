package br.com.proposta.entidades;

import br.com.proposta.transferenciadados.respostas.RespostaBloqueio;
import br.com.proposta.entidades.Enums.StatusBloqueio;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private OffsetDateTime instanteBloqueio;

    @NotBlank
    private String internetProtocol;

    @NotBlank
    private String userAgent;

    private StatusBloqueio statusBloqueio;

    @ManyToOne
    private Cartao cartao;

    public Bloqueio(@NotBlank String internetProtocol, @NotBlank String userAgent, StatusBloqueio statusBloqueio) {
        this.instanteBloqueio = OffsetDateTime.now();
        this.internetProtocol = internetProtocol;
        this.userAgent = userAgent;
        this.statusBloqueio = statusBloqueio;
    }

    public Bloqueio(List<String> IPeUserAgent, RespostaBloqueio bloqueio) {
        this.instanteBloqueio = OffsetDateTime.now();
        this.internetProtocol = IPeUserAgent.get(0);
        this.userAgent = IPeUserAgent.get(1);
        this.statusBloqueio = StatusBloqueio.valueOf(bloqueio.getResultado());
    }

    public String getId() {
        return id;
    }
}
