package br.com.zup.nossocartao.cartaobloqueado.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.cartaobloqueado.CartaoBloqueado;
import br.com.zup.nossocartao.cartaobloqueado.repository.CartaoBloqueadoRepository;
import br.com.zup.nossocartao.integracao.cartao.CartaoBloqueadoRequest;
import br.com.zup.nossocartao.integracao.cartao.CartaoBloqueadoResponse;
import br.com.zup.nossocartao.integracao.cartao.CartaoFeignClient;

//8
@Service
public class CartaoService {

	// 1
	private final CartaoBloqueadoRepository cartaoBloqueadoRepository;

	// 1
	private final CartaoFeignClient feingCartao;

	private final Logger logger = LoggerFactory.getLogger(CartaoService.class);

	public CartaoService(CartaoBloqueadoRepository cartaoBloqueadoRepository, CartaoFeignClient feingCartao) {
		this.cartaoBloqueadoRepository = cartaoBloqueadoRepository;
		this.feingCartao = feingCartao;
	}

	public Optional<Long> bloquearCartao(String idCartao, String enderecoIp, String userAgent) {

		CartaoBloqueado dadosCartaoBloqueado = new CartaoBloqueado(idCartao, enderecoIp, userAgent);

		// 1
		ResponseEntity<CartaoBloqueadoResponse> bloquearCartao = feingCartao
				.bloquearCartao(dadosCartaoBloqueado.getIdCartao(), new CartaoBloqueadoRequest());

		// 2
		if (bloquearCartao.getStatusCode().isError()) {
			logger.warn("Erro ao comunicar com o sistema legado!");
			return Optional.empty();
		}

		// 2
		dadosCartaoBloqueado.setStatus(bloquearCartao.getBody().getResultado());

		CartaoBloqueado salvarDadosBloqueio = cartaoBloqueadoRepository.save(dadosCartaoBloqueado);

		logger.info("Dados para bloquear cartão foi salvo no banco de dados!");

		// 1
		return Optional.of(salvarDadosBloqueio.getIdCartaoBloqueado());
	}
}
