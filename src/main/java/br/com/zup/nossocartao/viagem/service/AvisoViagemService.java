package br.com.zup.nossocartao.viagem.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.integracao.cartao.AvisoRequest;
import br.com.zup.nossocartao.integracao.cartao.CartaoFeignClient;
import br.com.zup.nossocartao.viagem.AvisoViagem;
import br.com.zup.nossocartao.viagem.controller.AvisoViagemRequest;
import br.com.zup.nossocartao.viagem.repository.AvisoViagemRepository;

@Service
public class AvisoViagemService {

	private final AvisoViagemRepository avisoViagemRepository;

	private final CartaoFeignClient feignclient;

	public AvisoViagemService(AvisoViagemRepository avisoViagemRepository, CartaoFeignClient feignclient) {
		this.avisoViagemRepository = avisoViagemRepository;
		this.feignclient = feignclient;
	}

	public Optional<Long> avisarViagem(AvisoViagemRequest dadosAviso) {
		AvisoViagem aviso = dadosAviso.gerarDadosViagem();

		AvisoRequest request = new AvisoRequest(aviso);

		ResponseEntity<?> retornoLegado = feignclient.avisoViagem(aviso.getIdCartao(), request);

		if (retornoLegado.getStatusCode().isError()) {
			return Optional.empty();
		}

		AvisoViagem salvo = avisoViagemRepository.save(aviso);

		return Optional.of(salvo.getIdAvisoViagem());

	}
}
