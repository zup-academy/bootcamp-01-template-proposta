package br.com.zup.nossocartao.proposta.service;

import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.integracao.SolicitacaoAnalise;
import br.com.zup.nossocartao.integracao.SolicitacaoAnaliseRequest;
import br.com.zup.nossocartao.integracao.SolicitacaoAnaliseResponse;
import br.com.zup.nossocartao.proposta.Proposta;
import br.com.zup.nossocartao.proposta.controller.NovaPropostaRequest;
import br.com.zup.nossocartao.proposta.controller.NovaPropostaResponse;
import br.com.zup.nossocartao.proposta.repository.PropostaRepository;

@Service
public class PropostaService {

	private PropostaRepository propostaRepository;

	private SolicitacaoAnalise solicitacaoAnalise;

	public PropostaService(PropostaRepository propostaRepository, SolicitacaoAnalise solicitacaoAnalise) {
		this.propostaRepository = propostaRepository;
		this.solicitacaoAnalise = solicitacaoAnalise;
	}

	public NovaPropostaResponse salvarProposta(NovaPropostaRequest dadosRequisitados) {
		Proposta novaProposta = dadosRequisitados.gerarProposta();

		Proposta propostaSalva = propostaRepository.save(novaProposta);

		avaliacaoFinanceira(propostaSalva);

		NovaPropostaResponse dadosPropostaResponse = new NovaPropostaResponse(propostaSalva);

		return dadosPropostaResponse;

	}

	private void avaliacaoFinanceira(Proposta propostaSalva) {
		SolicitacaoAnaliseRequest statusAnalise = new SolicitacaoAnaliseRequest(propostaSalva);
		SolicitacaoAnaliseResponse resultadoStatus = solicitacaoAnalise.resultadoAnalise(statusAnalise);
		propostaSalva.alterarStatusProposta(resultadoStatus.getResultadoSolicitacao().getStatusAvaliacao());
		propostaRepository.save(propostaSalva);

	}

	public boolean verificaDocumento(String cpfCnpj) {
		boolean exiteCpfCnpj = propostaRepository.existsByCpfCnpj(cpfCnpj);
		return exiteCpfCnpj;
	}
}
