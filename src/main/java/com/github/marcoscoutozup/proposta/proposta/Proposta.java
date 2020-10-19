package com.github.marcoscoutozup.proposta.proposta;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.marcoscoutozup.proposta.proposta.enums.StatusDaProposta;
import com.github.marcoscoutozup.proposta.validator.cpfoucnpj.CpfOuCnpj;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NamedQuery(name = "findPropostaByDocumento", query = "select p from Proposta p where p.documento = :documento")
public class Proposta {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotBlank
    @CpfOuCnpj
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusDaProposta statusDaProposta;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String documento, @NotBlank String email, @NotBlank String nome, @NotBlank String endereco, @NotNull BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public UUID getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public void modificarStatusDaProposta(StatusDaProposta statusDaProposta) {
        Assert.notNull(statusDaProposta, "O status da proposta não pode ser nulo para modificação");
        this.statusDaProposta = statusDaProposta;
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "id=" + id +
                ", documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", salario=" + salario +
                ", statusDaProposta=" + statusDaProposta +
                '}';
    }
}
