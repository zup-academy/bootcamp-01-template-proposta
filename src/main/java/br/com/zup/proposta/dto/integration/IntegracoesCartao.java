package br.com.zup.proposta.dto.integration;

import br.com.zup.proposta.dto.CartaoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(url ="${host.cartao}", name = "integracaoCartao")
public interface IntegracoesCartao {

    @GetMapping("/api/cartoes")
    public CartaoRequest buscarCartaoPorIdProposta(@RequestParam String idProposta);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    public Map<String, String> bloquearCartao(@PathVariable("id") String numeroCartao,
                                 Map<String, String> sistemaResponsavel);

    @PostMapping("/api/cartoes/{id}/avisos")
    public Map<String, String> notificarAvisoViagem(@PathVariable("id") String numeroCartao,
                                                    Map<String, String> dadosViagem);

    @PostMapping("/api/cartoes/{id}/carteiras")
    public void associarCarteira(@PathVariable("id") String numeroCartao,
                                           Map<String, String> dadosCarteira);

}
