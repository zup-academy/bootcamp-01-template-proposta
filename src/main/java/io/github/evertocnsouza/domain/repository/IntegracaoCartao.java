package io.github.evertocnsouza.domain.repository;

import io.github.evertocnsouza.rest.dto.response.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "cartao", url = "${host.cartao}")
public interface IntegracaoCartao {

    @GetMapping("/api/cartoes")
    CartaoResponse pesquisaIdProposta(@RequestParam Long idProposta);

}