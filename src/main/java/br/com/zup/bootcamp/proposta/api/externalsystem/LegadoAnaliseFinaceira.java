package br.com.zup.bootcamp.proposta.api.externalsystem;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Scope(scopeName = "prototype")
@FeignClient(name = "DadosFinanceiros", url = "${analise.host}" )
public interface LegadoAnaliseFinaceira {
    @PostMapping("/solicitacao")
    ResponseAvaliacaoFiananceiraDto SolicitaAnalise(@RequestBody RequestAvaliacaoFinanceiraDto requestAvaliacaoFinanceiraDto);
}
