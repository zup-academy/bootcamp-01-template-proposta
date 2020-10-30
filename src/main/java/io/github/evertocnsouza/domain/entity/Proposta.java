package io.github.evertocnsouza.domain.entity;

import io.github.evertocnsouza.domain.enums.StatusAvaliacaoProposta;
import io.github.evertocnsouza.validation.annotation.CpfCnpj;
import org.springframework.util.Assert;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@NamedQuery(name = "findPropostaByStatus", query = "select p from Proposta p where p.statusAvaliacao = :statusAvaliacao")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @Positive
    private BigDecimal salario;

    @CpfCnpj
    @NotBlank
    private String documento;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusAvaliacaoProposta statusAvaliacao;

    @OneToOne
    private Cartao cartao;

    @Deprecated
    public Proposta() {

    }

    public Proposta(@Email @NotBlank String email, @NotBlank String nome,
                    @NotBlank String endereco, @Positive BigDecimal salario,
                    @CpfCnpj String documento) {
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.documento = documento;
        this.statusAvaliacao = StatusAvaliacaoProposta.NAO_ELEGIVEL;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return this.documento;
    }

    public BigDecimal getSalario() { return salario; }

    public String getEmail() {return email; }

    public String getEndereco() {return endereco; }

    public void atualizaStatus(StatusAvaliacaoProposta statusAvaliacao) {
        Assert.isTrue(this.statusAvaliacao.equals(StatusAvaliacaoProposta.NAO_ELEGIVEL),
                "Proposta elegível! Não é possível fazer a troca");
        this.statusAvaliacao = statusAvaliacao;
    }
    public StatusAvaliacaoProposta getStatusAvaliacao() {
        return statusAvaliacao;
    }

    public String getNome() {
        return nome;
    }


    public boolean verificarSeNaoExisteCartao() {
        return Objects.isNull(cartao);
    }

    public void incluirCartaoNaProposta(Cartao cartao) {
        this.cartao = cartao;
    }
}
