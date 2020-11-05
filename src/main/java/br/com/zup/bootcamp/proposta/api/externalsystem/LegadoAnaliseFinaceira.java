package br.com.zup.bootcamp.proposta.api.externalsystem;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "DadosFinanceiros", url = "${analise.host}" )
public interface LegadoAnaliseFinaceira {
    @PostMapping("/solicitacao")
    ResponseAvaliacaoFiananceiraDto SolicitaAnalise(@RequestBody RequestAvaliacaoFinanceiraDto requestAvaliacaoFinanceiraDto);
}
