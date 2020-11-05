package dev.arielalvesdutra.proposta.http_clients;

import dev.arielalvesdutra.proposta.http_clients.dtos.CartaoRetornoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Cliente HTTP para o Serviço de Cartões.
 */
@FeignClient(value = "cartao", url = "${cartao.url}")
public interface CartaoHttpClient {

    /**
     * Busca cartão pelo ID da proposta.
     *
     * @param idProposta
     * @return
     */
    @GetMapping("/api/cartoes")
    CartaoRetornoDTO buscaCartao(@RequestParam String idProposta);
}
