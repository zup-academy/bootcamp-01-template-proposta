package com.github.marcoscoutozup.proposta.cartao;

import com.github.marcoscoutozup.proposta.bloqueio.enums.BloqueioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "cartao", url = "${host.cartao}")
public interface CartaoClient {

    @GetMapping("/api/cartoes")
    CartaoResponse pesquisarCartaoPorIdDaProposta(@RequestParam String idProposta);

    @PostMapping("/api/cartoes/{idCartao}/bloqueios")
    BloqueioResponse bloquearCartao(@PathVariable UUID idCartao, @RequestBody Object bloqueioRequest);

}
