package br.com.zup.proposta.model.cartao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class RecuperacaoSenha {

    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    private LocalDateTime pedidoEm;
    @NotNull
    private String ip;
    @NotNull
    private String usuario;
    @NotNull @ManyToOne
    private Cartao cartao;

    @Deprecated
    public RecuperacaoSenha(){}

    public RecuperacaoSenha(String ip, String usuario, Cartao cartao) {
        this.pedidoEm = LocalDateTime.now();
        this.ip = ip;
        this.usuario = usuario;
        this.cartao = cartao;
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getPedidoEm() {
        return this.pedidoEm;
    }

    public String getIp() {
        return this.ip;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Cartao getCartao() {
        return this.cartao;
    }

}
