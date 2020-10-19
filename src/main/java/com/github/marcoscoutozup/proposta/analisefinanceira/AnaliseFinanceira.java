package com.github.marcoscoutozup.proposta.analisefinanceira;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analisefinanceira", url = "${host.analise-financeira}")
public interface AnaliseFinanceira {

    @PostMapping("/api/solicitacao")
    AnaliseFinanceiraResponse processaAnaliseFinanceira(@RequestBody AnaliseFinanceiraRequest request);
}
