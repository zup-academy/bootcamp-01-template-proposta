package br.com.proposta.entidades;

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
    private OffsetDateTime instanteBloqueio = OffsetDateTime.now();

    @NotBlank
    private String internetProtocol;

    @NotBlank
    private String userAgent;


    @ManyToOne
    private Cartao cartao;

    public Bloqueio(@NotBlank String internetProtocol, @NotBlank String userAgent) {
        this.internetProtocol = internetProtocol;
        this.userAgent = userAgent;
    }

    public Bloqueio(List<String> IPeUserAgent, Cartao cartao) {
        this.internetProtocol = IPeUserAgent.get(0);
        this.userAgent = IPeUserAgent.get(1);
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public void associaCartao(Cartao cartao){

        this.cartao = cartao;
    }

}
