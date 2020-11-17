package br.com.zup.proposta.model.cartao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class CartaoRenegociacao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private Number quantidade;
    private Number valor;
    private LocalDateTime dataDeCriacao;
    @ManyToOne
    private Cartao cartao;

    //@Deprecated
    public CartaoRenegociacao(){}

    public CartaoRenegociacao(String id, Number quantidade, Number valor, LocalDateTime dataDeCriacao, Cartao cartao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return this.id;
    }

    public Number getQuantidade() {
        return this.quantidade;
    }

    public Number getValor() {
        return this.valor;
    }

    public LocalDateTime getDataDeCriacao() {
        return this.dataDeCriacao;
    }

}
