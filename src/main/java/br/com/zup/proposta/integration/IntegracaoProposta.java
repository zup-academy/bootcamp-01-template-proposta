package br.com.zup.proposta.integration;

import br.com.zup.proposta.dto.request.SolicitacaoAnaliseRequest;
import br.com.zup.proposta.dto.response.ResultadoAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${avaliacao.host}", name = "integracoes")

public interface IntegracaoProposta {

    @PostMapping("/api/solicitacao")
    ResponseEntity<ResultadoAnaliseResponse> avaliaproposta(@RequestBody SolicitacaoAnaliseRequest request);
}
