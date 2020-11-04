package br.com.cartao.proposta.domain.request;


import br.com.cartao.proposta.domain.model.Cartao;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bloqueioCartao")
public class Bloqueio {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String ipAddressCliente;

    private String userAgent;

    private LocalDateTime instanteBloqueio;
    @ManyToOne
    private Cartao cartao;
    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(String ipCliente, String userAgent, Cartao cartao) {
        this.ipAddressCliente = ipCliente;
        this.userAgent = userAgent;
        this.cartao = cartao;
        this.instanteBloqueio = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getIpAddressCliente() {
        return ipAddressCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public LocalDateTime getInstanteBloqueio() {
        return instanteBloqueio;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
