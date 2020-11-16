package br.com.zup.cartaoproposta.clienteswebservices;

import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitante;
import br.com.zup.cartaoproposta.entities.analisesolicitante.AnaliseSolicitanteRetorno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Contagem de carga intrínseca da classe: 2
 */

@FeignClient(value = "analise-cartoes", url = "${url.analise-cartoes}")
public interface AnaliseCartoesClient {

    @RequestMapping(method = RequestMethod.POST, value = "/solicitacao")
    //2
    AnaliseSolicitanteRetorno solicitacaoAnaliseResource(AnaliseSolicitante solicitante);
}
