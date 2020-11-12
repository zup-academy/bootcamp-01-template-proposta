package br.com.zup.proposta.cartao;

import br.com.zup.proposta.aviso.AvisoRequest;
import br.com.zup.proposta.carteira.CarteiraRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(url = "${feign.integracao.url.integracao-cartao}", name = "integracaoCartao")
public interface IntegracaoCartao {

    @GetMapping("/api/cartoes")
    CartaoResponse pesquisaIdProposta(@RequestParam String idProposta);

    @PostMapping("/api/cartoes/{idCartao}/bloqueios")
    ResponseEntity bloquearCartao(@PathVariable String idCartao, @RequestBody Map request);

    @PostMapping("/api/cartoes/{idCartao}/avisos")
    ResponseEntity enviarAvisoViagem(@PathVariable String idCartao, @RequestBody AvisoRequest avisoRequest);

    @PostMapping("/api/cartoes/{idCartao}/carteiras")
    ResponseEntity enviarCarteiraAssociada(@PathVariable String idCartao, @RequestBody Map carteiraRequest);
}
