package dev.arielalvesdutra.proposta.http_clients.dtos;

import dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoBloqueioStatus;

import static dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoBloqueioStatus.FALHA;

/**
 * Resultado da requisição de bloqueio de cartão.
 */
public class ResultadoBloqueioDTO {

    private ResultadoBloqueioStatus resultado;

    public ResultadoBloqueioDTO() {
    }

    public ResultadoBloqueioStatus getResultado() {
        return resultado;
    }

    public ResultadoBloqueioDTO setResultado(ResultadoBloqueioStatus resultado) {
        this.resultado = resultado;
        return this;
    }

    @Override
    public String toString() {
        return "ResultadoBloqueioDTO{" +
                "resultado=" + resultado +
                '}';
    }

    /**
     * Retorna verdadeiro caso o resultado da solicitação de bloqueio
     * tenha retornado falha.
     *
     * @return
     */
    public boolean falhou() {
        return resultado == FALHA;
    }
}
