package br.com.zup.cartaoproposta.clienteswebservices;

import br.com.zup.cartaoproposta.entities.cartao.DadosCartaoRetornoLegado;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Contagem de carga intrínseca da classe: 1
 */

@FeignClient(value = "busca-cartoes", url = "${url.cartoes}")
public interface CartoesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/cartoes")
    //1
    DadosCartaoRetornoLegado buscaDadosCartoesResource(@RequestParam String idProposta);

}
