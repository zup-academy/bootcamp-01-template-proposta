package br.com.proposta.entidades;

import br.com.proposta.entidades.Enums.StatusCarteira;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String carteira;

    private StatusCarteira status;

    @ManyToOne
    private Cartao cartao;

    public Carteira(@NotBlank String carteira, StatusCarteira status, Cartao cartao) {
        this.carteira = carteira;
        this.status = status;
        this.cartao = cartao;
    }
}
