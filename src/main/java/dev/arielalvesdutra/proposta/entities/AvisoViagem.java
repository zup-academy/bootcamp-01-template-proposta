package dev.arielalvesdutra.proposta.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Table(name = "aviso_viagem")
@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull(message = "{termino.obrigatorio}")
    @Future(message = "{termino.futuro}")
    private LocalDate termino;
    @NotBlank(message = "{destino.obrigatorio}")
    private String destino;
    @NotBlank(message = "{ip.obrigatorio}")
    private String ip;
    @NotBlank(message = "{user_agent.obrigatorio}")
    private String userAgent;
    private OffsetDateTime cadatradoEm = OffsetDateTime.now();

    @NotNull(message = "{cartao.obrigatorio}")
    @ManyToOne
    private Cartao cartao;

    public AvisoViagem() {
    }

    public String getId() {
        return id;
    }

    public AvisoViagem setId(String id) {
        this.id = id;
        return this;
    }

    public LocalDate getTermino() {
        return termino;
    }

    public AvisoViagem setTermino(LocalDate termino) {
        this.termino = termino;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public AvisoViagem setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public AvisoViagem setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public String getDestino() {
        return destino;
    }

    public AvisoViagem setDestino(String destino) {
        this.destino = destino;
        return this;
    }

    public OffsetDateTime getCadatradoEm() {
        return cadatradoEm;
    }

    public AvisoViagem setCadatradoEm(OffsetDateTime cadatradoEm) {
        this.cadatradoEm = cadatradoEm;
        return this;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public AvisoViagem setCartao(Cartao cartao) {
        this.cartao = cartao;
        return this;
    }

    @Override
    public String toString() {
        return "AvisoViagem{" +
                "id='" + id + '\'' +
                ", termino=" + termino +
                ", ip='" + ip + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", cadatradoEm=" + cadatradoEm +
                '}';
    }
}
