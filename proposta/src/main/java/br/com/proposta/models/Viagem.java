package br.com.proposta.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Entity
public class Viagem {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String destino;

    @ManyToOne
    private Cartao cartao;

    @Future
    @NotNull
    private OffsetDateTime termino;

    @NotNull
    private OffsetDateTime avisadoEm;

    @NotBlank
    private String internetProtocol;

    @NotBlank
    private String userAgent;


    public Viagem(Cartao cartao, @Future OffsetDateTime termino,
                  String internetProtocol, String userAgent) {
        this.cartao = cartao;
        this.termino = termino;
        this.avisadoEm = OffsetDateTime.now();
        this.internetProtocol = internetProtocol;
        this.userAgent = userAgent;
    }

}
