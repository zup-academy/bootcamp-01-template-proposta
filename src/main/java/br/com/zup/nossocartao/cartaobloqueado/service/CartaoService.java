package br.com.zup.nossocartao.cartaobloqueado.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.cartaobloqueado.CartaoBloqueado;
import br.com.zup.nossocartao.cartaobloqueado.repository.CartaoBloqueadoRepository;
import br.com.zup.nossocartao.integracao.cartao.CartaoBloqueadoRequest;
import br.com.zup.nossocartao.integracao.cartao.CartaoBloqueadoResponse;
import br.com.zup.nossocartao.integracao.cartao.CartaoFeignClient;

@Service
public class CartaoService {

	private final CartaoBloqueadoRepository cartaoBloqueadoRepository;

	private final CartaoFeignClient feingCartao;

	public CartaoService(CartaoBloqueadoRepository cartaoBloqueadoRepository, CartaoFeignClient feingCartao) {
		this.cartaoBloqueadoRepository = cartaoBloqueadoRepository;
		this.feingCartao = feingCartao;
	}

	public Optional<Long> bloquearCartao(String idCartao, String enderecoIp, String userAgent) {

		CartaoBloqueado dadosCartaoBloqueado = new CartaoBloqueado(idCartao, enderecoIp, userAgent);

		ResponseEntity<CartaoBloqueadoResponse> bloquearCartao = feingCartao
				.bloquearCartao(dadosCartaoBloqueado.getIdCartao(), new CartaoBloqueadoRequest());

		if (bloquearCartao.getStatusCode().isError()) {
			return Optional.empty();
		}

		dadosCartaoBloqueado.setStatus(bloquearCartao.getBody().getResultado());

		CartaoBloqueado salvarDadosBloqueio = cartaoBloqueadoRepository.save(dadosCartaoBloqueado);
		return Optional.of(salvarDadosBloqueio.getIdCartaoBloqueado());
	}
}
