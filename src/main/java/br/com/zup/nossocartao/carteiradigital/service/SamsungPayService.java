package br.com.zup.nossocartao.carteiradigital.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.carteiradigital.SamsungPay;
import br.com.zup.nossocartao.carteiradigital.repository.SamsungPayRepository;
import br.com.zup.nossocartao.integracao.cartao.CartaoFeignClient;
import br.com.zup.nossocartao.integracao.cartao.CartaoResponse;
import br.com.zup.nossocartao.integracao.carteiradigital.CarteiraFeign;
import br.com.zup.nossocartao.integracao.carteiradigital.SamsungPayRequestFeign;
import br.com.zup.nossocartao.integracao.carteiradigital.SamsungPayResponseFeign;

@Service
public class SamsungPayService {

	private final SamsungPayRepository samsungPayRepository;

	private final CarteiraFeign carteiraFeign;

	private final CartaoFeignClient cartaoFeign;

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

		if (!buscarDadosCartaoPorIdCartao.getBody().getCarteiras().isEmpty()) {
			return Optional.empty();
		}

		SamsungPayRequestFeign dadosFeign = new SamsungPayRequestFeign(email);

		ResponseEntity<SamsungPayResponseFeign> associarCarteiraSamsungPay = carteiraFeign
				.associarCarteiraSamsungPay(idCartao, dadosFeign);

		if (associarCarteiraSamsungPay.getStatusCode().isError()) {
			return Optional.empty();
		}

		SamsungPay salvo = samsungPayRepository.save(dadosSamsungPay);

		return Optional.of(salvo.getId());
	}
}
