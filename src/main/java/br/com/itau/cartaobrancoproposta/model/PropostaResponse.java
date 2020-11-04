package br.com.itau.cartaobrancoproposta.model;

import java.math.BigDecimal;

public class PropostaResponse {

    private final String id;
    private final String documento;
    private final String email;
    private final String nome;
    private final String endereco;
    private final BigDecimal salario;
    private final Restricao restricao;
    private final Cartao cartao;

    public PropostaResponse(String id, String documento, String email, String nome, String endereco, BigDecimal salario, Restricao restricao, Cartao cartao) {
        this.id = id;
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.restricao = restricao;
        this.cartao = cartao;
    }

    public String getId() {
        return id;
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

    public Restricao getRestricao() {
        return restricao;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public PropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
        this.restricao = proposta.getRestricao();
        this.cartao = proposta.getCartao();
    }
}