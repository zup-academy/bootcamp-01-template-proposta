package br.com.zup.cartaoproposta.clienteswebservices;

import br.com.zup.cartaoproposta.entities.cartao.DadosCartaoRetornoLegado;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.BloqueioRequest;
import br.com.zup.cartaoproposta.entities.cartao.bloqueio.BloqueioRetornoLegado;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Contagem de carga intrínseca da classe: 1
 */

@FeignClient(value = "busca-cartoes", url = "${url.cartoes}")
public interface CartoesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/cartoes")
    //1
    DadosCartaoRetornoLegado dadosCartaoPelaPropostaResource(@RequestParam String idProposta);

    @RequestMapping(method = RequestMethod.POST, value = "/cartoes/{id}/bloqueios")
    //1
    BloqueioRetornoLegado bloqueiaCartoesResource(@PathVariable("id") String idCartao, BloqueioRequest bloqueioRequest);

    @RequestMapping(method = RequestMethod.GET, value = "/cartoes/{id}")
    DadosCartaoRetornoLegado dadosCartaoPeloId(@PathVariable("id") String idCartao);
}
