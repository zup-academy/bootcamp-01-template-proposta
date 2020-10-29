package br.com.proposta.models;

import br.com.proposta.models.Enums.StatusBloqueio;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String idCartao;

    private OffsetDateTime instanteBloqueio;

    private String internetProtocol;

    private String userAgent;

    private StatusBloqueio statusBloqueio;

    public Bloqueio(String idCartao, String internetProtocol, String userAgent, StatusBloqueio statusBloqueio) {
        this.idCartao = idCartao;
        this.instanteBloqueio = OffsetDateTime.now();
        this.internetProtocol = internetProtocol;
        this.userAgent = userAgent;
        this.statusBloqueio = statusBloqueio;
    }

}
