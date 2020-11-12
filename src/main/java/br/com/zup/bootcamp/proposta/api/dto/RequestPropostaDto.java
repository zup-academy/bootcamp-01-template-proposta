package br.com.zup.bootcamp.proposta.api.dto;

import br.com.zup.bootcamp.proposta.api.handler.CPForCNPJ;
import br.com.zup.bootcamp.proposta.domain.entity.Proposta;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class RequestPropostaDto {

    @NotBlank @CPForCNPJ
    private final String documento;
    @Email
    private final String email;
    @NotBlank
    private final String nome;
    @NotBlank
    private final String endereco;
    @Positive
    private final BigDecimal salario;

    public RequestPropostaDto(@NotBlank String documento, @Email String email, @NotBlank String nome, @NotBlank String endereco, @Positive BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toEntity() {
        return new Proposta(documento, email, nome, endereco, salario);
    }


    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }
}
