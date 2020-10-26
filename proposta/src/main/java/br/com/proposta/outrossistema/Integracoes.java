package br.com.proposta.outrossistema;

import br.com.proposta.dtos.requests.SolicitacaoAnalise;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(url = "http://localhost:9999", name = "integracoes")
public interface Integracoes {

    @PostMapping("/api/solicitacao")
    String avaliaproposta(SolicitacaoAnalise solicitacaoAnalise);

}
