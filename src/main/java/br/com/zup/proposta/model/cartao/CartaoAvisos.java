package br.com.zup.proposta.model.cartao;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoAvisosResponse;

@Entity
public class CartaoAvisos {

    @Id @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull @Future
    private LocalDate validoAte;
    @NotNull
    private String destino;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public CartaoAvisos(){}

    public CartaoAvisos(CartaoAvisosResponse response, Cartao cartao) {
        this.validoAte = response.getValidoAte();
        this.destino = response.getDestino();
        this.cartao = cartao;
    }

    public String getId() {
        return this.id;
    }

    public LocalDate getValidoAte() {
        return this.validoAte;
    }

    public String getDestino() {
        return this.destino;
    }

}
