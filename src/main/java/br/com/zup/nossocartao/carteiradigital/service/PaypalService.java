package br.com.zup.nossocartao.carteiradigital.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.carteiradigital.Paypal;
import br.com.zup.nossocartao.carteiradigital.repository.PaypalRepository;
import br.com.zup.nossocartao.integracao.cartao.CartaoFeignClient;
import br.com.zup.nossocartao.integracao.cartao.CartaoResponse;
import br.com.zup.nossocartao.integracao.carteiradigital.CarteiraFeign;
import br.com.zup.nossocartao.integracao.carteiradigital.PaypalRequestFeign;
import br.com.zup.nossocartao.integracao.carteiradigital.PaypalResponseFeign;

@Service
public class PaypalService {

	private final PaypalRepository paypalRepository;

	private final CarteiraFeign carteiraFeign;

	private final CartaoFeignClient cartaoFeign;

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

		if (!buscarDadosCartaoPorIdCartao.getBody().getCarteiras().isEmpty()) {
			return Optional.empty();
		}

		PaypalRequestFeign dadosFeign = new PaypalRequestFeign(email);

		ResponseEntity<PaypalResponseFeign> associarCarteiraPaypal = carteiraFeign.associarCarteiraPaypal(idCartao,
				dadosFeign);

		if (associarCarteiraPaypal.getStatusCode().isError()) {
			return Optional.empty();
		}

		Paypal salvo = paypalRepository.save(dadosPaypal);

		return Optional.of(salvo.getId());

	}
}
