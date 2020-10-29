package io.github.evertocnsouza.domain.repository;

import io.github.evertocnsouza.rest.dto.PropostaSolicitadaRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "integracoes", url = "${feign.analise-url}")
public interface Integracoes {
    @PostMapping("api/solicitacao")
    public String avalia(PropostaSolicitadaRequest request);
}

