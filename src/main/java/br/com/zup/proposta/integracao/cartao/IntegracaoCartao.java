package br.com.zup.proposta.integracao.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8888", name = "integracaoCartao")
public interface IntegracaoCartao {
    @GetMapping("/api/cartoes")
    public CartaoResponse pesquisaIdProposta(@RequestParam String idProposta);
}
