package br.com.proposta.entidades;

import br.com.proposta.dtos.responses.AvisoViagemResponse;
import br.com.proposta.entidades.Enums.StatusAviso;
import br.com.proposta.repositories.CartaoRepository;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class Aviso {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    private Cartao cartao;

    @NotNull
    private LocalDateTime avisadoEm = LocalDateTime.now();

    @NotBlank
    private String internetProtocol;

    @NotBlank
    private String userAgent;

    @Enumerated(EnumType.STRING)
    private StatusAviso status;


    public Aviso(@NotBlank String internetProtocol,
                 @NotBlank String userAgent, StatusAviso status) {
        this.internetProtocol = internetProtocol;
        this.userAgent = userAgent;
        this.status = status;
    }


    public Aviso(List<String> IPeUserAgent, AvisoViagemResponse resposta) {
        this.internetProtocol = IPeUserAgent.get(0);
        this.userAgent = IPeUserAgent.get(1);
        this.status = StatusAviso.valueOf(resposta.getResultado());
    }

    public String getId() {
        return id;
    }

    public void associaCartao(Cartao cartao){
        this.cartao = cartao;
    }
}


