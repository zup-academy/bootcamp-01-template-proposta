package br.com.itau.cartaobrancoproposta.client;

import br.com.itau.cartaobrancoproposta.model.SolicitacaoCartao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${endereco.api.cartao}", name = "cartao")
public interface CartaoClient {

    @GetMapping("/api/cartoes")
    SolicitacaoCartao buscaCartao(@RequestParam("idProposta") String idProposta);
}
