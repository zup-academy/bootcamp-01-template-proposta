package br.com.zup.proposta.model.cartao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class CartaoVencimento {

    @Id
    private String id;
    @NotNull
    private Integer dia;
    @NotNull
    private LocalDateTime dataDeCriacao;
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public CartaoVencimento(){}

    public CartaoVencimento(String id, Integer dia, LocalDateTime dataDeCriacao, Cartao cartao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
        this.cartao = cartao;
    }

    public String getId() {
        return this.id;
    }

    public Integer getDia() {
        return this.dia;
    }

    public LocalDateTime getDataDeCriacao() {
        return this.dataDeCriacao;
    }

}
