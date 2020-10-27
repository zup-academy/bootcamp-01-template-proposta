package io.github.evertocnsouza.rest.dto;

import io.github.evertocnsouza.domain.entity.Proposta;
import io.github.evertocnsouza.validation.CpfCnpj;
import io.github.evertocnsouza.validation.UniqueValue;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PropostaRequest {

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
    private BigDecimal salario;

    public PropostaRequest(@NotBlank String documento,
                           @NotBlank @Email String email,
                           @NotBlank String nome,
                           @NotBlank String endereco,
                           @NotBlank BigDecimal salario) {
        super();
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getDocumento() {
        return documento;
    }

    public Proposta ToModel() {
        return new Proposta(documento, email, nome, endereco, salario);
    }
}

