package br.com.zup.nossocartao.cartao.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.cartao.CartaoBloqueado;
import br.com.zup.nossocartao.cartao.repository.CartaoBloqueadoRepository;
import br.com.zup.nossocartao.integracao.cartao.CartaoBloqueadoRequest;
import br.com.zup.nossocartao.integracao.cartao.CartaoBloqueadoResponse;
import br.com.zup.nossocartao.integracao.cartao.SolicitacaoCartaoFeignClient;

@Service
public class CartaoService {

	private final CartaoBloqueadoRepository cartaoBloqueadoRepository;

	private final SolicitacaoCartaoFeignClient feingCartao;

	public CartaoService(CartaoBloqueadoRepository cartaoBloqueadoRepository,
			SolicitacaoCartaoFeignClient feingCartao) {
		this.cartaoBloqueadoRepository = cartaoBloqueadoRepository;
		this.feingCartao = feingCartao;
	}

	public Optional<Long> bloquearCartao(CartaoBloqueado dadosBloqueio) {

		ResponseEntity<CartaoBloqueadoResponse> bloquearCartao = feingCartao.bloquearCartao(dadosBloqueio.getIdCartao(),
				new CartaoBloqueadoRequest());

		if (bloquearCartao.getStatusCode().isError()) {
			return Optional.empty();
		}

		CartaoBloqueado salvarDadosBloqueio = cartaoBloqueadoRepository.save(dadosBloqueio);
		return Optional.of(salvarDadosBloqueio.getIdCartaoBloqueado());
	}
}
