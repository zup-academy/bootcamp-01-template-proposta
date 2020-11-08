package br.com.zup.proposta.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class RecuperacaoSenha {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    @ManyToOne
    @NotNull
    @Valid
    private Cartao cartao;
    private @NotBlank String userAgent;
    private @NotBlank  String ipClient;
    private LocalDateTime instanteCriacao;

    public RecuperacaoSenha(Cartao cartao, String userAgent, String ipClient) {
        this.cartao = cartao;
        this.userAgent = userAgent;
        this.ipClient = ipClient;
        this.instanteCriacao = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "RecuperacaoSenha{" +
                "cartao=" + cartao.getNumero() +
                ", userAgent='" + userAgent + '\'' +
                ", ipClient='" + ipClient + '\'' +
                ", instanteCriacao=" + instanteCriacao +
                '}';
    }

}
