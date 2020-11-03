package br.com.zup.proposta.integracao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:9999", name = "integracaoProposta")
public interface IntegracaoProposta {
    @PostMapping("/api/solicitacao")
    public String avalia(DocumentoRequest request);
}
