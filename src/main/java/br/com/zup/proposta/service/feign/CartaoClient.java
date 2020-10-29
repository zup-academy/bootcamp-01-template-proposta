package br.com.zup.proposta.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.proposta.controllers.form.AnaliseRequestForm;

@FeignClient(value = "cartao", url = "${feign.cartao-url}")
public interface CartaoClient {
    
    @RequestMapping(method = RequestMethod.POST, value = "api/cartoes")
    void solicitaCartao(@RequestBody AnaliseRequestForm form);
}
