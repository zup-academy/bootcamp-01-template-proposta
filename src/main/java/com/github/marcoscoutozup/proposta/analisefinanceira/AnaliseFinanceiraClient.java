package com.github.marcoscoutozup.proposta.analisefinanceira;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "analisefinanceira", url = "${host.analise-financeira}")
public interface AnaliseFinanceiraClient {

    @PostMapping("/api/solicitacao")
    ResponseEntity<AnaliseFinanceiraResponse> processaAnaliseFinanceira(@RequestBody Map request);
}
