package br.com.proposta.integracoes;

import br.com.proposta.dtos.requests.AnaliseDaPropostaRequest;
import br.com.proposta.dtos.responses.AnaliseDaPropostaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(url = "${avaliacao.host}", name = "integracoes")
public interface IntegracaoApiAnalise {

    @PostMapping
    ResponseEntity<AnaliseDaPropostaResponse> avaliaproposta(@RequestBody AnaliseDaPropostaRequest requisicaoSolicitarAnaliseDaProposta);

}
