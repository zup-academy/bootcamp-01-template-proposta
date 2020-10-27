package br.com.zup.proposta.controllers.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.service.validadores.anotações.CpfCnpj;

public class SolicitacaoForm {
    
    @NotNull @CpfCnpj
    private String documento;
    @NotNull @Email
    private String email;
    @NotNull @NotBlank
    private String nome;
    @NotNull @NotBlank
    private String endereco;
    @NotNull @Positive
    private Double salario;

    public SolicitacaoForm(@NotNull String documento, @NotNull String email, @NotNull String nome,
        @NotNull String endereco, @NotNull Double salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toProposta() {
        return new Proposta(this.documento, this.email, this.nome, this.endereco, this.salario);
    }
}
