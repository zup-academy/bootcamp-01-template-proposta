package br.com.proposta.services;

import br.com.proposta.dtos.requests.SolicitacaoAnaliseRequest;
import br.com.proposta.dtos.responses.ResultadoAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(url = "${avaliacao.host}", name = "integracoes")
public interface IntegracaoPropostaService {

    @PostMapping
    ResponseEntity<ResultadoAnaliseResponse> avaliaproposta(@RequestBody SolicitacaoAnaliseRequest solicitacaoAnaliseRequest);

}
