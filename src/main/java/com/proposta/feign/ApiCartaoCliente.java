package com.proposta.feign;

import com.proposta.bloqueiodecartao.BloqueioRequest;
import com.proposta.bloqueiodecartao.BloqueioResponse;
import com.proposta.feign.request.SolicitacaoCriarCartao;
import com.proposta.feign.response.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "http://localhost:8888", name = "AnaliseNovoCartao")
public interface ApiCartaoCliente {

    @Async
    @PostMapping("/api/cartoes")
    void solicitarCartao(@RequestBody SolicitacaoCriarCartao request);

    @GetMapping("/api/cartoes")
    @ResponseBody
    ResponseEntity<CartaoResponse> verificarCriacaoCartao(@RequestParam Long idProposta);

//    @Async
    @PostMapping("/api/cartoes/{idCartao}/bloqueios")
    BloqueioResponse bloquearCartao(@PathVariable String idCartao, @RequestBody BloqueioRequest bloqueioRequest);
}