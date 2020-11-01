package com.proposta.criacaocartao;

public class NovoCartaoRequest {

    private String documento;

    private String nome;

    private String idProposta;

    public NovoCartaoRequest(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }
}
