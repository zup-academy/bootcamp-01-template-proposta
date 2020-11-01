package br.com.itau.cartaobrancoproposta.model;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${endereco.analise.cliente}", name = "analise")
public interface AnaliseClient {

    @PostMapping("api/solicitacao")
    Solicitacao buscaAnaliseFinanceira(SolicitacaoRequest request);
}
