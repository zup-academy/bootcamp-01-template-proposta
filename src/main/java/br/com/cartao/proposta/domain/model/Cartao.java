package br.com.cartao.proposta.domain.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "cartao")
public class Cartao {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private String cartaoId;
    @OneToOne
    private Proposta proposta;
    @Deprecated
    public Cartao() {
    }

    public Cartao(String cartaoId, Proposta proposta) {
        this.cartaoId = cartaoId;
        this.proposta = proposta;
    }

    public String getId() {
        return id;
    }

    public String getCartaoId() {
        return cartaoId;
    }

    public Proposta getProposta() {
        return proposta;
    }
}
