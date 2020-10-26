package br.com.proposta.dtos.requests;

import br.com.proposta.models.Proposta;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PropostaRequest {


    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    /* pode ser CPF ou CNPJ */
    private String numeroIdentificacao;


    public PropostaRequest(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String endereco,
                           @NotNull @Positive BigDecimal salario, String numeroIdentificacao) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
        this.numeroIdentificacao = numeroIdentificacao;
    }

    public Proposta toModel(){

        return new Proposta(nome, email, endereco, salario, numeroIdentificacao);

    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getNumeroIdentificacao() {
        return numeroIdentificacao;
    }

    public void setNumeroIdentificacao(String numeroIdentificacao) {
        this.numeroIdentificacao = numeroIdentificacao;
    }
}
