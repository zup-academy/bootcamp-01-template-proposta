package br.com.zup.proposta.proposta;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class NovaPropostaRequest {
    @NotBlank
    public String documento;
    @NotBlank
    @Email
    public String email;
    @NotBlank
    public String nome;
    @NotBlank
    public String endereco;
    @NotNull
    @PositiveOrZero
    public BigDecimal salario;

    public NovaPropostaRequest(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toModel() {
        return new Proposta(this.documento, this.email, this.nome, this.endereco, this.salario);
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }
}
