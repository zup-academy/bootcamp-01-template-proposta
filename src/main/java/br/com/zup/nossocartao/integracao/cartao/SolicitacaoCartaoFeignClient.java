package br.com.zup.nossocartao.integracao.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "solicitacaoCartao", url = "${api.cartoes}")
public interface SolicitacaoCartaoFeignClient {

	@RequestMapping(method = RequestMethod.POST, value = "/cartoes")
	void emitirCartao(CartaoRequest request);

	@RequestMapping(method = RequestMethod.GET, value = "/cartoes")
	CartaoResponse buscarDadosCartao(@RequestParam(value = "idProposta") String idProposta);
}
