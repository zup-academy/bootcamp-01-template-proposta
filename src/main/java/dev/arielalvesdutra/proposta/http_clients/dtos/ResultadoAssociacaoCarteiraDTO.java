package dev.arielalvesdutra.proposta.http_clients.dtos;

import dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAssociacaoCarteiraStatus;

public class ResultadoAssociacaoCarteiraDTO {

    private String id;
    private ResultadoAssociacaoCarteiraStatus resultado;

    public ResultadoAssociacaoCarteiraDTO() {
    }

    public String getId() {
        return id;
    }

    public ResultadoAssociacaoCarteiraDTO setId(String id) {
        this.id = id;
        return this;
    }

    public ResultadoAssociacaoCarteiraStatus getResultado() {
        return resultado;
    }

    public ResultadoAssociacaoCarteiraDTO setResultado(ResultadoAssociacaoCarteiraStatus resultado) {
        this.resultado = resultado;
        return this;
    }

    @Override
    public String toString() {
        return "ResultadoAssociacaoCarteiraDTO{" +
                "id='" + id + '\'' +
                ", resultado=" + resultado +
                '}';
    }
}
