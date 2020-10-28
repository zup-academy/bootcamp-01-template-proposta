package br.com.proposta.models;

import br.com.proposta.models.Enums.StatusBloqueio;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cartao cartao;

    private OffsetDateTime instanteBloqueio;

    private String internetProtocol;

    private String userAgent;

    private StatusBloqueio statusBloqueio;


    public Bloqueio(String internetProtocol, String userAgent, StatusBloqueio statusBloqueio, Cartao cartao) {

        this.cartao = cartao;
        this.instanteBloqueio = OffsetDateTime.now();
        this.internetProtocol = internetProtocol;
        this.userAgent = userAgent;
        this.statusBloqueio = statusBloqueio;

    }


}
