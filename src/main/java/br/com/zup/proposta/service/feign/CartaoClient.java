package br.com.zup.proposta.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.proposta.controllers.apiResponses.AvisoViagemResponse;
import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoAvisosResponse;
import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoResponse;
import br.com.zup.proposta.controllers.apiResponses.cartao.SolicitaBloqueioResponse;
import br.com.zup.proposta.controllers.form.SolicitaBloqueioForm;

@FeignClient(value = "cartao", url = "${feign.cartao-url}")
public interface CartaoClient {
    
    @RequestMapping(method = RequestMethod.GET, value = "api/cartoes?idProposta={id}")
    CartaoResponse solicitaCartao(@PathVariable String id);

    @RequestMapping(method = RequestMethod.POST, value = "api/cartoes/{id}/bloqueios")
    SolicitaBloqueioResponse solicitaBloqueio(@PathVariable String id, @RequestBody SolicitaBloqueioForm form);

    @RequestMapping(method = RequestMethod.POST, value = "api/cartoes/{id}/avisos")
    AvisoViagemResponse solicitaViagem(@PathVariable String id, @RequestBody CartaoAvisosResponse form);
}
