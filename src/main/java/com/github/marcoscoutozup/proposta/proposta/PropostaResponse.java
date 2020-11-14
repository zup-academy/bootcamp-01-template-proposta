package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.proposta.enums.StatusDaProposta;

public class PropostaResponse {

    private String email;
    private String nome;
                //1
    private StatusDaProposta statusDaProposta;
                                //2
    public PropostaResponse(Proposta proposta) {
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.statusDaProposta = proposta.getStatusDaProposta();
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


    public StatusDaProposta getStatusDaProposta() {
        return statusDaProposta;
    }

    public void setStatusDaProposta(StatusDaProposta statusDaProposta) {
        this.statusDaProposta = statusDaProposta;
    }
}
