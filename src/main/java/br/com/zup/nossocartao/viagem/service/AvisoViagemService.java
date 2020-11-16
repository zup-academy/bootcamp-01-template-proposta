package br.com.zup.nossocartao.viagem.service;

import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.viagem.AvisoViagem;
import br.com.zup.nossocartao.viagem.controller.AvisoViagemRequest;
import br.com.zup.nossocartao.viagem.repository.AvisoViagemRepository;

@Service
public class AvisoViagemService {

	private final AvisoViagemRepository avisoViagemRepository;

	public AvisoViagemService(AvisoViagemRepository avisoViagemRepository) {
		this.avisoViagemRepository = avisoViagemRepository;
	}

	public Long avisarViagem(AvisoViagemRequest dadosAviso) {
		AvisoViagem aviso = dadosAviso.gerarDadosViagem();

		AvisoViagem salvo = avisoViagemRepository.save(aviso);

		return salvo.getIdAvisoViagem();

	}
}
