package com.github.marcoscoutozup.proposta.recuperarsenha;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class RecuperarSenha {

    @Id
    @GeneratedValue
    private UUID id;

    @CreationTimestamp
    private LocalDateTime instante;

    private String ip;

    private String userAgent;

    @ManyToOne //1
    private Cartao cartao;

    @Deprecated
    public RecuperarSenha() {
    }

    public RecuperarSenha(String ip, String userAgent, Cartao cartao) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public UUID getId() {
        return id;
    }
}
