package br.com.zup.nossocartao.integracao.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "solicitacaoCartao", url = "${api.cartoes}")
public interface SolicitacaoCartaoFeignClient {

	@RequestMapping(method = RequestMethod.POST, value = "/cartoes")
	void emitirCartao(CartaoRequest request);

	@RequestMapping(method = RequestMethod.GET, value = "/cartoes")
	CartaoResponse buscarDadosCartaoPorIdProposta(@RequestParam(value = "idProposta") String idProposta);

	@RequestMapping(method = RequestMethod.POST, value = "/cartoes/{id}/bloqueios")
	ResponseEntity<CartaoBloqueadoResponse> bloquearCartao(@PathVariable(value = "id") String id,
			@RequestBody CartaoBloqueadoRequest request);

	@RequestMapping(method = RequestMethod.GET, value = "/cartoes/{id}")
	ResponseEntity<CartaoResponse> buscarDadosCartaoPorIdCartao(@PathVariable(value = "id") String idCartao);

}
