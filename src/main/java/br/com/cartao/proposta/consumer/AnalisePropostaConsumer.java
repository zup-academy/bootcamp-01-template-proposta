package br.com.cartao.proposta.consumer;

import br.com.cartao.proposta.domain.request.AnalisePropostaRequest;
import br.com.cartao.proposta.domain.response.AnalisePropostResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "analise", url = "http://localhost:9999")
public interface AnalisePropostaConsumer {

    @RequestMapping(method = RequestMethod.POST, path = "/api/solicitacao" )
    public AnalisePropostResponse avaliacaoFinanceira(AnalisePropostaRequest analisePropostaRequest);
}
