package com.github.marcoscoutozup.proposta.analisefinanceira;

import java.util.UUID;

public class AnaliseFinanceiraRequest {

    private String documento;
    private String nome;
    private UUID idProposta;

    public AnaliseFinanceiraRequest(String documento, String nome, UUID idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public UUID getIdProposta() {
        return idProposta;
    }
}
