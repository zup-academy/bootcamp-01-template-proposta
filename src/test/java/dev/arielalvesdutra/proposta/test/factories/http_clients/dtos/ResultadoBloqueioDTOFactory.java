package dev.arielalvesdutra.proposta.test.factories.http_clients.dtos;

import dev.arielalvesdutra.proposta.http_clients.dtos.ResultadoBloqueioDTO;
import dev.arielalvesdutra.proposta.http_clients.dtos.enums.ResultadoBloqueioStatus;

public class ResultadoBloqueioDTOFactory {


    public static ResultadoBloqueioDTO resultadoBlequeioSucesso() {
        return new ResultadoBloqueioDTO()
                .setResultado(ResultadoBloqueioStatus.BLOQUEADO);
    }
}
