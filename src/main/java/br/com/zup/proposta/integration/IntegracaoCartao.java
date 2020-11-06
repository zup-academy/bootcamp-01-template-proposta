package br.com.zup.proposta.integration;

import br.com.zup.proposta.dto.request.AvisoViagemRequest;
import br.com.zup.proposta.dto.response.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@FeignClient(url = "${cartao.host}", name = "cartao")
public interface IntegracaoCartao {

    @GetMapping("/api/cartoes")
    CartaoResponse pesquisarCartaoPorIdDaProposta(@RequestParam String idProposta);

    @PostMapping("/api/cartoes/{cartaoID}/bloqueios")
    ResponseEntity bloquearCartao(@PathVariable UUID cartaoID, @RequestBody Map bloqueioRequest);

    @PostMapping("/api/cartoes/{cartaoID}/avisos")
    ResponseEntity enviarAvisoDeViagem(@PathVariable UUID cartaoID, @RequestBody AvisoViagemRequest avisoViagemRequest);

    @PostMapping("/api/cartoes/{cartaoID}/carteiras")
    ResponseEntity associarCarteira(@PathVariable UUID cartaoID, @RequestBody Map carteiraRequest);
}
