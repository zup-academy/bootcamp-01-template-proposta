package br.com.cartao.proposta.consumer;

import br.com.cartao.proposta.domain.model.Cartao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "criacaoCartao", url = "${feign.url-cartao}")
public interface CriacaoCartaoConsumer {

    @RequestMapping(method = RequestMethod.GET, path = "/api/cartoes")
    public Cartao verificaCartaoCriado(@RequestParam String idProposta);
}
