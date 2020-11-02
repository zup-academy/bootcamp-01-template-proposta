package br.com.zup.nossocartao.integracao.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "solicitacaoCartao", url = "${api.cartoes}")
public interface SolicitacaoCartao {

	@RequestMapping(method = RequestMethod.POST, value = "/cartoes")
	void emitirCartao(CartaoRequest request);
}
