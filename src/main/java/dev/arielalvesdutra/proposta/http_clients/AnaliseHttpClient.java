package dev.arielalvesdutra.proposta.http_clients;

import dev.arielalvesdutra.proposta.http_clients.dtos.ResultadoAnaliseDTO;
import dev.arielalvesdutra.proposta.http_clients.dtos.SolicitacaoAnaliseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Cliente HTTP para o Serviço de Análise.
 */
@FeignClient(name = "analise", url = "${analise.url}")
public interface AnaliseHttpClient {

    /**
     * Requisição para analise de dados financeiros do solicitante.
     *
     * Será verificado se o solicitante possui restriçies financeiras.
     *
     * @param solicitacaoAnaliseDTO
     *
     * @return {@link ResultadoAnaliseDTO }
     */
    @PostMapping("/api/solicitacao")
    ResultadoAnaliseDTO analisaDadosFinanceiros(@RequestBody SolicitacaoAnaliseDTO solicitacaoAnaliseDTO);
}

