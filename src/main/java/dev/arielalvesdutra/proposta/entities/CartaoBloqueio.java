package dev.arielalvesdutra.proposta.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Entity
@Table(name = "cartao_bloqueio")
public class CartaoBloqueio {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank(message = "{ip.obrigatorio}")
    private String ip;
    @NotBlank(message = "{user_agent.obrigatorio}")
    private String userAgent;
    private OffsetDateTime cadastradoEm = OffsetDateTime.now();

    @NotNull(message = "{cartao.obrigatorio}")
    @ManyToOne
    private Cartao cartao;

    public CartaoBloqueio() {
    }

    public String getId() {
        return id;
    }

    public CartaoBloqueio setId(String id) {
        this.id = id;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public CartaoBloqueio setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public CartaoBloqueio setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }

    public CartaoBloqueio setCadastradoEm(OffsetDateTime cadastradoEm) {
        this.cadastradoEm = cadastradoEm;
        return this;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public CartaoBloqueio setCartao(Cartao cartao) {
        this.cartao = cartao;
        return this;
    }

    @Override
    public String toString() {
        return "CartaoBloqueio{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", cadastradoEm=" + cadastradoEm +
                '}';
    }
}

