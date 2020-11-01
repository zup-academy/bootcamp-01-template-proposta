package br.com.zup.proposta.dto.integration;

import br.com.zup.proposta.dto.CartaoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url ="${host.cartao}", name = "integracaoCartao")
public interface IntegracoesCartao {

    @GetMapping("/api/cartoes")
    public CartaoRequest buscarCartaoPorIdProposta(@RequestParam String idProposta);


}
