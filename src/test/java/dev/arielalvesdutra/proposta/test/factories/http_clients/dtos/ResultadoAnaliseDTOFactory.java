package dev.arielalvesdutra.proposta.test.factories.http_clients.dtos;

import dev.arielalvesdutra.proposta.http_clients.dtos.ResultadoAnaliseDTO;
import dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAnaliseStatus;

import static dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAnaliseStatus.COM_RESTRICAO;
import static dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoAnaliseStatus.SEM_RESTRICAO;

/**
 * Factory para auxiliar implementação de testes.
 */
public class ResultadoAnaliseDTOFactory {

    public static ResultadoAnaliseDTO resultadoAnaliseSemRestricao() {
        return new ResultadoAnaliseDTO()
                .setResultadoSolicitacao(SEM_RESTRICAO);

    }

    public static ResultadoAnaliseDTO resultadoAnaliseComRestricao() {
        return new ResultadoAnaliseDTO()
                .setResultadoSolicitacao(COM_RESTRICAO);
    }
}
