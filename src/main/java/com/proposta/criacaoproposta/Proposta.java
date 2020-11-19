package com.proposta.criacaoproposta;

import com.proposta.criacaocartao.Cartao;
import com.proposta.feign.request.SolicitacaoAnaliseRequest;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String documento;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    private StatusProposta status = StatusProposta.NAO_ANALISADA;

    @OneToOne(cascade = CascadeType.ALL)
    private Cartao cartao;

    @Deprecated
    public Proposta() {

    }

    public Proposta(@NotBlank DocumentoLimpo documentoLimpo, @Email @NotBlank String email, @NotBlank String nome,
                    @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.documento = documentoLimpo.hash();
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public SolicitacaoAnaliseRequest toAnalise() {
        return new SolicitacaoAnaliseRequest(documento, nome, id.toString());
    }


    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public void setStatus(StatusProposta status) {
        this.status = status;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

}
