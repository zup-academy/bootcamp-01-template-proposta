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

    private String idCartao;

    @Future
    @NotNull
    private OffsetDateTime termino;

    @NotNull
    private OffsetDateTime avisadoEm;

    @NotBlank
    private String internetProtocol;

    @NotBlank
    private String userAgent;


    public Viagem(@NotBlank String destino, String idCartao, @Future @NotNull OffsetDateTime termino,
                  @NotNull OffsetDateTime avisadoEm, @NotBlank String internetProtocol, @NotBlank String userAgent) {
        this.destino = destino;
        this.idCartao = idCartao;
        this.termino = termino;
        this.avisadoEm = avisadoEm;
        this.internetProtocol = internetProtocol;
        this.userAgent = userAgent;
    }
}
