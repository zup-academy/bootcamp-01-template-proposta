package br.com.zup.proposta.analiseproposta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${feign.integracao.url.analise-proposta}", name = "integracaoProposta")
public interface IntegracaoProposta {
    @PostMapping("/api/solicitacao")
    String avalia(DocumentoRequest request);
}
