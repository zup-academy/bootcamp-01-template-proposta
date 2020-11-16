package br.com.zup.nossocartao.validador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.com.zup.nossocartao.integracao.cartao.CartaoResponse;
import br.com.zup.nossocartao.integracao.cartao.SolicitacaoCartaoFeignClient;

@Component
public class ValidadorIdCartao {

	@Autowired
	private SolicitacaoCartaoFeignClient cartaoFeingClient;

	public boolean idCartaoNaoExiste(String idCartao) {

		ResponseEntity<CartaoResponse> retornoApi = cartaoFeingClient.buscarDadosCartaoPorIdCartao(idCartao);

		if (retornoApi.getStatusCode().isError()) {
			return true;
		}

		return false;
	}

}
