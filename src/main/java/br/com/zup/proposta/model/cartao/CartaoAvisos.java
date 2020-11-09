package br.com.zup.proposta.model.cartao;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class CartaoAvisos {

    @Id @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    private LocalDateTime avisadoEm;
    @NotNull @Future
    private LocalDate validoAte;
    @NotNull
    private String destino;
    @NotNull
    private String usuario;
    @NotNull
    private String ipAddress;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public CartaoAvisos(){}

    public CartaoAvisos(LocalDate validoAte, String destino, String usuario, String ipAddress, Cartao cartao) {
        this.avisadoEm = LocalDateTime.now();
        this.validoAte = validoAte;
        this.destino = destino;
        this.usuario = usuario;
        this.ipAddress = ipAddress;
        this.cartao = cartao;
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getAvisadoEm() {
        return this.avisadoEm;
    }

    public LocalDate getValidoAte() {
        return this.validoAte;
    }

    public String getDestino() {
        return this.destino;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public Cartao getCartao() {
        return this.cartao;
    }

}
