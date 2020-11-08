package br.com.zup.proposta.dto.integration;

import br.com.zup.proposta.dto.CartaoRequest;
import br.com.zup.proposta.model.enums.StatusCartao;
import feign.Body;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@FeignClient(url ="${host.cartao}", name = "integracaoCartao")
public interface IntegracoesCartao {

    @GetMapping("/api/cartoes")
    public CartaoRequest buscarCartaoPorIdProposta(@RequestParam String idProposta);

    @PostMapping(value = "/api/cartoes/{id}/bloqueios")
    public Map<String, String> bloquearCartao(@PathVariable("id") String numeroCartao,
                                 Map<String, String> sistemaResponsavel);

}
