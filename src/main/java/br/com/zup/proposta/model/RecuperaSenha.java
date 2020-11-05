package br.com.zup.proposta.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class RecuperaSenha {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @CreationTimestamp
    private LocalDateTime instanteSolicitacao;

    private String ip;

    private String userAgent;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public RecuperaSenha() {
    }

    public RecuperaSenha(String ip, String userAgent, Cartao cartao) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public LocalDateTime getInstanteSolicitacao() {
        return instanteSolicitacao;
    }

    public UUID getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
