package br.com.zup.proposta.integracao.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${feign.integracao.url.integracao-cartao}", name = "integracaoCartao")
public interface IntegracaoCartao {
    @GetMapping("/api/cartoes")
    CartaoResponse pesquisaIdProposta(@RequestParam String idProposta);
}
