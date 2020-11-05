package br.com.cartao.proposta.consumer;

import br.com.cartao.proposta.domain.enums.SolicitacaoBloqueioIntegracaoResponse;
import br.com.cartao.proposta.domain.request.SolicitacaoBloqueioRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "avisoBloqueioCartao", url = "${feign.url-cartao}")
public interface AvisaBloqueioCartao {

    @RequestMapping(method = RequestMethod.POST, path = "/api/cartoes/{id}/bloqueios")
    public SolicitacaoBloqueioIntegracaoResponse avisaBloqueioCartao(@PathVariable("id") String idCartao, @RequestBody SolicitacaoBloqueioRequest solicitacaoBloqueioRequest);
}
