package com.github.marcoscoutozup.proposta.analisefinanceira;

import com.github.marcoscoutozup.proposta.proposta.enums.ResultadoStatusProposta;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analisefinanceira", url = "${host.analise-financeira}", fallbackFactory = AnaliseFinanceiraClient.AnaliseFinanceiraClientFallbackFactory.class)
public interface AnaliseFinanceiraClient {

    Logger log = LoggerFactory.getLogger(AnaliseFinanceiraClient.class);

    @PostMapping("/api/solicitacao")
    AnaliseFinanceiraResponse processaAnaliseFinanceira(@RequestBody AnaliseFinanceiraRequest request);

    @Component
    class AnaliseFinanceiraClientFallbackFactory implements FallbackFactory<AnaliseFinanceiraClient>{

        @Override
        public AnaliseFinanceiraClient create(Throwable throwable) {
            log.error("[ANÁLISE FINANCEIRA] Retorno de erro do sistema de cartões: {}", throwable.getMessage());
            return (request) -> {
               AnaliseFinanceiraResponse analiseFinanceiraResponse = new AnaliseFinanceiraResponse();
               analiseFinanceiraResponse.setResultadoSolicitacao(ResultadoStatusProposta.COM_RESTRICAO);
               return analiseFinanceiraResponse;
           };
        }
    }

}
