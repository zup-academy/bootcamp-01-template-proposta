package br.com.cartao.proposta.consumer;

import br.com.cartao.proposta.domain.response.CartaoResponseSistemaLegado;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "criacaoCartao", url = "${feign.url-cartao}")
public interface CriacaoCartaoConsumer {

    @RequestMapping(method = RequestMethod.GET, path = "/api/cartoes")
    public CartaoResponseSistemaLegado verificaCartaoCriado(@RequestParam String idProposta);
}
