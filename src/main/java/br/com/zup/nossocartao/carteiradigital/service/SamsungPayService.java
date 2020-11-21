package br.com.zup.nossocartao.carteiradigital.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.carteiradigital.SamsungPay;
import br.com.zup.nossocartao.carteiradigital.repository.SamsungPayRepository;
import br.com.zup.nossocartao.integracao.cartao.CartaoFeignClient;
import br.com.zup.nossocartao.integracao.cartao.CartaoResponse;
import br.com.zup.nossocartao.integracao.carteiradigital.CarteiraFeign;
import br.com.zup.nossocartao.integracao.carteiradigital.SamsungPayRequestFeign;
import br.com.zup.nossocartao.integracao.carteiradigital.SamsungPayResponseFeign;

//7
@Service
public class SamsungPayService {

	// 1
	private final SamsungPayRepository samsungPayRepository;

	// 1
	private final CarteiraFeign carteiraFeign;

	// 1
	private final CartaoFeignClient cartaoFeign;

	private final Logger logger = LoggerFactory.getLogger(SamsungPayService.class);

	public SamsungPayService(SamsungPayRepository samsungPayRepository, CarteiraFeign carteiraFeign,
			CartaoFeignClient cartaoFeign) {
		this.samsungPayRepository = samsungPayRepository;
		this.carteiraFeign = carteiraFeign;
		this.cartaoFeign = cartaoFeign;
	}

	public Optional<Long> associarSamsumgPay(String idCartao, String email) {

		SamsungPay dadosSamsungPay = new SamsungPay(email, idCartao);

		ResponseEntity<CartaoResponse> buscarDadosCartaoPorIdCartao = cartaoFeign
				.buscarDadosCartaoPorIdCartao(idCartao);

		CartaoResponse response = buscarDadosCartaoPorIdCartao.getBody();

		// 1
		if (!response.getCarteiras().isEmpty()) {

			logger.warn("Os dados do cartão já foram cadastrados!");

			return Optional.empty();
		}

		SamsungPayRequestFeign dadosFeign = new SamsungPayRequestFeign(email);

		ResponseEntity<SamsungPayResponseFeign> associarCarteiraSamsungPay = carteiraFeign
				.associarCarteiraSamsungPay(idCartao, dadosFeign);

		// 2
		if (associarCarteiraSamsungPay.getStatusCode().isError()) {
			logger.warn("Erro de comunicacao com legado");
			return Optional.empty();
		}

		SamsungPay salvo = samsungPayRepository.save(dadosSamsungPay);

		logger.info("SamsungPay foi salvo no banco de dados.");
		// 1
		return Optional.of(salvo.getId());

	}
}
