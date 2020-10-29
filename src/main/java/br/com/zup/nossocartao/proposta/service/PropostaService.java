package br.com.zup.nossocartao.proposta.service;

import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.proposta.Proposta;
import br.com.zup.nossocartao.proposta.controller.NovaPropostaRequest;
import br.com.zup.nossocartao.proposta.controller.NovaPropostaResponse;
import br.com.zup.nossocartao.proposta.repository.PropostaRepository;

@Service
public class PropostaService {

	private PropostaRepository propostaRepository;

	public PropostaService(PropostaRepository propostaRepository) {
		this.propostaRepository = propostaRepository;
	}

	public NovaPropostaResponse salvarProposta(NovaPropostaRequest dadosRequisitados) {
		Proposta novaProposta = dadosRequisitados.gerarProposta();

		Proposta propostaSalva = propostaRepository.save(novaProposta);

		NovaPropostaResponse dadosPropostaResponse = new NovaPropostaResponse(propostaSalva);

		return dadosPropostaResponse;

	}

}
