package io.github.evertocnsouza.domain.repository;

import io.github.evertocnsouza.rest.dto.response.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartao", url = "${host.cartao}")
public interface IntegracaoCartao {

    @GetMapping("/api/cartoes")
    CartaoResponse pesquisaIdProposta(@RequestParam Long idProposta);

}
