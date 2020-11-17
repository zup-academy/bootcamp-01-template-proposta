package br.com.zup.proposta.model.cartao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.Nullable;

import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoBloqueioResponse;

@Entity
public class CartaoBloqueio {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    private LocalDateTime bloqueadoEm;
    @Nullable
    private String ipAddress;
    @Nullable
    private String usuario;
    @NotNull
    private String sistemaResponsavel;
    @NotNull
    private boolean ativo;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public CartaoBloqueio(){}

    public CartaoBloqueio(CartaoBloqueioResponse response, Cartao cartao) {
        this.bloqueadoEm = response.getBloqueadoEm();
        this.ipAddress = null;
        this.usuario = null;
        this.sistemaResponsavel = response.getSistemaResponsavel();
        this.ativo = response.getAtivo();
        this.cartao = cartao;
    }

    public CartaoBloqueio(String ipAddress, String usuario, Cartao cartao) {
        this.bloqueadoEm = LocalDateTime.now();
        this.ipAddress = ipAddress;
        this.usuario = usuario;
        this.sistemaResponsavel = "proposta";
        this.ativo = false;
        this.cartao = cartao;
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getBloqueadoEm() {
        return this.bloqueadoEm;
    }

    public String getSistemaResponsavel() {
        return this.sistemaResponsavel;
    }

    public boolean getAtivo() {
        return this.ativo;
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Cartao getCartao() {
        return this.cartao;
    }

}
