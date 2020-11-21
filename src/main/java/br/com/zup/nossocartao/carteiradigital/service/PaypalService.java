package br.com.zup.nossocartao.carteiradigital.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.carteiradigital.Paypal;
import br.com.zup.nossocartao.carteiradigital.repository.PaypalRepository;
import br.com.zup.nossocartao.integracao.cartao.CartaoFeignClient;
import br.com.zup.nossocartao.integracao.cartao.CartaoResponse;
import br.com.zup.nossocartao.integracao.carteiradigital.CarteiraFeign;
import br.com.zup.nossocartao.integracao.carteiradigital.PaypalRequestFeign;
import br.com.zup.nossocartao.integracao.carteiradigital.PaypalResponseFeign;

//7
@Service
public class PaypalService {

	// 1
	private final PaypalRepository paypalRepository;

	// 1
	private final CarteiraFeign carteiraFeign;

	// 1
	private final CartaoFeignClient cartaoFeign;

	private final Logger logger = LoggerFactory.getLogger(PaypalService.class);

	public PaypalService(PaypalRepository paypalRepository, CarteiraFeign carteiraFeign,
			CartaoFeignClient cartaoFeign) {
		this.paypalRepository = paypalRepository;
		this.carteiraFeign = carteiraFeign;
		this.cartaoFeign = cartaoFeign;
	}

	public Optional<Long> associarPaypal(String idCartao, String email) {

		Paypal dadosPaypal = new Paypal(idCartao, email);

		ResponseEntity<CartaoResponse> buscarDadosCartaoPorIdCartao = cartaoFeign
				.buscarDadosCartaoPorIdCartao(idCartao);

		CartaoResponse response = buscarDadosCartaoPorIdCartao.getBody();

		// 1
		if (!response.getCarteiras().isEmpty()) {
			logger.warn("Os dados do cartão já foram cadastrados!");

			return Optional.empty();
		}

		PaypalRequestFeign dadosFeign = new PaypalRequestFeign(email);

		ResponseEntity<PaypalResponseFeign> associarCarteiraPaypal = carteiraFeign.associarCarteiraPaypal(idCartao,
				dadosFeign);

		// 2
		if (associarCarteiraPaypal.getStatusCode().isError()) {
			logger.warn("Erro de comunicacao com legado");
			return Optional.empty();
		}

		Paypal salvo = paypalRepository.save(dadosPaypal);

		logger.info("PayPal foi salvo no banco de dados.");
		// 1
		return Optional.of(salvo.getId());

	}
}
