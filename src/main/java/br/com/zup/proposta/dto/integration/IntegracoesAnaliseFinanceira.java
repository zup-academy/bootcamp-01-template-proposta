package br.com.zup.proposta.dto.integration;

import br.com.zup.proposta.dto.AvaliacaoPropostaRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url="${host.analise.financeira}", name = "integracoes")
public interface IntegracoesAnaliseFinanceira {

    @PostMapping("/api/solicitacao")
    public AvaliacaoPropostaRequest avalia(AvaliacaoPropostaRequest avaliacaoPropostaRequest);

}
