package io.github.evertocnsouza.domain.entity;

import io.github.evertocnsouza.domain.enums.StatusAvaliacaoProposta;
import io.github.evertocnsouza.validation.CpfCnpj;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @CpfCnpj
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    @NotNull
    private StatusAvaliacaoProposta statusAvaliacao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento,
                    @NotBlank @Email String email,
                    @NotBlank String nome,
                    @NotBlank String endereco,
                    @NotBlank BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.statusAvaliacao = StatusAvaliacaoProposta.nao_elegivel;
    }

    public void atualizaStatus(StatusAvaliacaoProposta statusAvaliacao) {
        Assert.isTrue(this.statusAvaliacao.equals(StatusAvaliacaoProposta.nao_elegivel),
                "uma vez que a proposta é elegível, não é possível trocar");
        this.statusAvaliacao = statusAvaliacao;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proposta)) return false;
        Proposta proposta = (Proposta) o;
        return Objects.equals(documento, proposta.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", salario=" + salario +
                '}';

    }
}


