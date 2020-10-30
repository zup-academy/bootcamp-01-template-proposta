package br.com.proposta.models;

import br.com.proposta.models.Enums.StatusAviso;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
public class Aviso {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String numeroCartao;

    @ManyToOne
    private Cartao cartao;

    @NotNull
    private LocalDateTime avisadoEm;

    @NotBlank
    private String internetProtocol;

    @NotBlank
    private String userAgent;


    private StatusAviso status;


    public Aviso(String numeroCartao, @NotBlank String internetProtocol, @NotBlank String userAgent, StatusAviso status) {
        this.numeroCartao = numeroCartao;
        this.avisadoEm = LocalDateTime.now();
        this.internetProtocol = internetProtocol;
        this.userAgent = userAgent;
        this.status = status;
    }
}
