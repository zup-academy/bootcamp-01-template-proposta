package br.com.zup.nossocartao.viagem.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.integracao.cartao.AvisoRequest;
import br.com.zup.nossocartao.integracao.cartao.CartaoFeignClient;
import br.com.zup.nossocartao.viagem.AvisoViagem;
import br.com.zup.nossocartao.viagem.controller.AvisoViagemRequest;
import br.com.zup.nossocartao.viagem.repository.AvisoViagemRepository;

//7
@Service
public class AvisoViagemService {

	// 1
	private final AvisoViagemRepository avisoViagemRepository;

	// 1
	private final CartaoFeignClient feignclient;

	private final Logger logger = LoggerFactory.getLogger(AvisoViagemService.class);

	public AvisoViagemService(AvisoViagemRepository avisoViagemRepository, CartaoFeignClient feignclient) {
		this.avisoViagemRepository = avisoViagemRepository;
		this.feignclient = feignclient;
	}

	public Optional<Long> avisarViagem(AvisoViagemRequest dadosAviso) {
		AvisoViagem aviso = dadosAviso.gerarDadosViagem();

		AvisoRequest request = new AvisoRequest(aviso);

		// 2
		ResponseEntity<?> retornoLegado = feignclient.avisoViagem(aviso.getIdCartao(), request);

		// 2
		if (retornoLegado.getStatusCode().isError()) {
			logger.warn("Erro de comunicaçao com o sistema logado");
			return Optional.empty();
		}

		AvisoViagem salvo = avisoViagemRepository.save(aviso);

		logger.info("Aviso de viagem salvo.");
		// 1
		return Optional.of(salvo.getIdAvisoViagem());

	}
}
