package dev.arielalvesdutra.proposta.http_clients.dtos;

import dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAvisoViagemStatus;

import static dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAvisoViagemStatus.FALHA;

public class ResultadoAvisoViagemDTO {

    private ResultadoAvisoViagemStatus resultado;

    public ResultadoAvisoViagemDTO() {
    }

    public ResultadoAvisoViagemStatus getResultado() {
        return resultado;
    }

    public ResultadoAvisoViagemDTO setResultado(ResultadoAvisoViagemStatus resultado) {
        this.resultado = resultado;
        return this;
    }

    public boolean falhou() {
        return resultado == FALHA;
    }
}
