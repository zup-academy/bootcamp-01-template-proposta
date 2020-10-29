package br.com.zup.proposta.integracao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:9999", name = "integracoes")
public interface Integracoes {
    @PostMapping("/api/solicitacao")
    public String avalia(DocumentoRequest request);
}
