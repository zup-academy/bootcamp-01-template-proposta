package br.com.zup.proposta.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.proposta.controllers.apiResponses.cartao.CartaoResponse;

@FeignClient(value = "cartao", url = "${feign.cartao-url}")
public interface CartaoClient {
    
    @RequestMapping(method = RequestMethod.GET, value = "api/cartoes?idProposta={id}")
    CartaoResponse solicitaCartao(@PathVariable String id);
}
