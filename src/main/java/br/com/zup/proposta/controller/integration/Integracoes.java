package br.com.zup.proposta.controller.integration;

import br.com.zup.proposta.dto.AvaliacaoPropostaRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url="http://localhost:9999/api/solicitacao", name = "integracoes")
public interface Integracoes {

    @PostMapping
    public AvaliacaoPropostaRequest avalia(AvaliacaoPropostaRequest avaliacaoPropostaRequest);

}
