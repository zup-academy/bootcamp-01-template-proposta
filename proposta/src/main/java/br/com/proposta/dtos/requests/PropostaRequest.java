package br.com.proposta.dtos.requests;

import br.com.proposta.entidades.Proposta;

import javax.validation.constraints.*;
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

    @NotBlank
    private String identificacao;


    public PropostaRequest(@NotBlank String nome, @NotBlank @Email String email,
                           @NotBlank String endereco, @NotNull @Positive BigDecimal salario, @NotBlank String identificacao) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
        this.identificacao = identificacao;
    }

    public Proposta toModel(){

        return new Proposta(nome, email, endereco, salario, identificacao);

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
        return identificacao;
    }

    public void setNumeroIdentificacao(String numeroIdentificacao) {
        this.identificacao = numeroIdentificacao;
    }
}
