package br.com.proposta.entidades;

import br.com.proposta.dtos.responses.AvisoViagemResponse;
import br.com.proposta.entidades.Enums.StatusAviso;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

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


    public Aviso(String numeroCartao, List<String> IPeUserAgent, AvisoViagemResponse resposta) {
        this.numeroCartao = numeroCartao;
        this.avisadoEm = LocalDateTime.now();
        this.internetProtocol = IPeUserAgent.get(0);
        this.userAgent = IPeUserAgent.get(1);
        this.status = StatusAviso.valueOf(resposta.getResultado());
    }

    public String getId() {
        return id;
    }
}


