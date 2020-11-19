package com.proposta.bloqueiodecartao;

public class BloqueioRequest {

    private String nomeDoSistema;

    public void setNomeDoSistema(String nomeDoSistema) {
        this.nomeDoSistema = nomeDoSistema;
    }

    public String getNomeDoSistema() {
        return nomeDoSistema;
    }

    public BloqueioRequest(String nomeDoSistema) {
        this.nomeDoSistema = nomeDoSistema;
    }
}
