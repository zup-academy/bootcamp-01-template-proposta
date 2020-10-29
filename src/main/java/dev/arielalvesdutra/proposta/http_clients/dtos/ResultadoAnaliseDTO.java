package dev.arielalvesdutra.proposta.http_clients.dtos;

import dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAnaliseStatus;

/**
 * Resultado da análise dos dados financeiros do solicitante.
 */
public class ResultadoAnaliseDTO {

    private ResultadoAnaliseStatus resultadoSolicitacao;

    public ResultadoAnaliseDTO() {
    }

    public ResultadoAnaliseStatus getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public ResultadoAnaliseDTO setResultadoSolicitacao(ResultadoAnaliseStatus resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
        return this;
    }
}
