package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Base64;

public class PropostaResponse {

    private final String id;
    private final String documento;
    private final String email;
    private final String nome;
    private final String endereco;
    private final BigDecimal salario;
    private final Restricao restricao;
    @JsonProperty(value = "cartao")
    private final CartaoResponse cartaoResponse;

    public PropostaResponse(String id, String documento, String email, String nome, String endereco, BigDecimal salario,
                            Restricao restricao, CartaoResponse cartaoResponse) {
        this.id = id;
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.restricao = restricao;
        this.cartaoResponse = cartaoResponse;
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

    public CartaoResponse getCartaoResponse() {
        return cartaoResponse;
    }

    public PropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.documento = this.descodificaDocumento(proposta.getDocumento());
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
        this.restricao = proposta.getRestricao();
        this.cartaoResponse = new CartaoResponse(proposta.getCartao());
    }

    private String descodificaDocumento(String documento) {
        byte[] decode = Base64.getDecoder().decode(documento.getBytes());
        return new String(decode);
    }
}