package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.proposta.enums.StatusDaProposta;

import java.math.BigDecimal;

public class PropostaResponse {

    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salario;
                //1
    private StatusDaProposta statusDaProposta;
                                //2
    public PropostaResponse(Proposta proposta) {
        this.documento = proposta.descriptografarDocumento();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
        this.statusDaProposta = proposta.getStatusDaProposta();
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public StatusDaProposta getStatusDaProposta() {
        return statusDaProposta;
    }

    public void setStatusDaProposta(StatusDaProposta statusDaProposta) {
        this.statusDaProposta = statusDaProposta;
    }
}
