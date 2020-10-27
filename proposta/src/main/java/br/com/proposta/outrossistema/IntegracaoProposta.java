package br.com.proposta.outrossistema;

import br.com.proposta.dtos.requests.SolicitacaoAnalise;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(url = "${avaliacao.host}", name = "integracoes")
public interface IntegracaoProposta {

    @PostMapping
    String avaliaproposta(SolicitacaoAnalise solicitacaoAnalise);

}
