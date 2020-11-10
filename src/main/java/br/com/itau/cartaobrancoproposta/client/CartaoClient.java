package br.com.itau.cartaobrancoproposta.client;

import br.com.itau.cartaobrancoproposta.model.SolicitacaoAvisoViagemCartaoRequest;
import br.com.itau.cartaobrancoproposta.model.SolicitacaoBloqueioCartao;
import br.com.itau.cartaobrancoproposta.model.SolicitacaoCartao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(url = "${endereco.api.cartao}", name = "cartao")
public interface CartaoClient {

    @GetMapping("/api/cartoes")
    SolicitacaoCartao buscaCartao(@RequestParam("idProposta") String idProposta);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    SolicitacaoBloqueioCartao bloqueiaCartao(@PathVariable("id") String numeroCartao, @RequestBody Map<String, String> sistemaResponsavelBody);

    @PostMapping("/api/cartoes/{id}/avisos")
    ResponseEntity<?> avisoViagem(@PathVariable("id") String numeroCartao, SolicitacaoAvisoViagemCartaoRequest solicitacaoAvisoRequest);
}
