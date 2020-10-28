package br.com.proposta.services;

import br.com.proposta.dtos.requests.SolicitacaoAnaliseRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(url = "${avaliacao.host}", name = "integracoes")
public interface IntegracaoPropostaService {

    @PostMapping
    String avaliaproposta(SolicitacaoAnaliseRequest solicitacaoAnaliseRequest);

}
