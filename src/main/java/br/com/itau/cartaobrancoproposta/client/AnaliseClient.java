package br.com.itau.cartaobrancoproposta.client;

import br.com.itau.cartaobrancoproposta.model.Solicitacao;
import br.com.itau.cartaobrancoproposta.model.SolicitacaoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${endereco.api.analise.financeira}", name = "analise")
public interface AnaliseClient {

    @PostMapping("api/solicitacao")
    Solicitacao buscaAnaliseFinanceira(SolicitacaoRequest request);
}
