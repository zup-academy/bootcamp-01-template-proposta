package br.com.zup.nossocartao.proposta.service;

import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.integracao.analise.SolicitacaoAnalise;
import br.com.zup.nossocartao.integracao.analise.SolicitacaoAnaliseRequest;
import br.com.zup.nossocartao.integracao.analise.SolicitacaoAnaliseResponse;
import br.com.zup.nossocartao.integracao.cartao.CartaoRequest;
import br.com.zup.nossocartao.integracao.cartao.SolicitacaoCartao;
import br.com.zup.nossocartao.proposta.Proposta;
import br.com.zup.nossocartao.proposta.controller.NovaPropostaRequest;
import br.com.zup.nossocartao.proposta.controller.NovaPropostaResponse;
import br.com.zup.nossocartao.proposta.repository.PropostaRepository;

@Service
public class PropostaService {

	private PropostaRepository propostaRepository;

	private SolicitacaoAnalise solicitacaoAnalise;

	private SolicitacaoCartao solicitacaoCartao;

	public PropostaService(PropostaRepository propostaRepository, SolicitacaoAnalise solicitacaoAnalise,
			SolicitacaoCartao solicitacaoCartao) {
		this.propostaRepository = propostaRepository;
		this.solicitacaoAnalise = solicitacaoAnalise;
		this.solicitacaoCartao = solicitacaoCartao;
	}

	public NovaPropostaResponse salvarProposta(NovaPropostaRequest dadosRequisitados) {
		Proposta novaProposta = dadosRequisitados.gerarProposta();

		Proposta propostaSalva = propostaRepository.save(novaProposta);

		avaliacaoFinanceira(propostaSalva);

		NovaPropostaResponse dadosPropostaResponse = new NovaPropostaResponse(propostaSalva);

		emitirCartao(propostaSalva);

		return dadosPropostaResponse;

	}

	private void emitirCartao(Proposta dadosPropostaCartao) {
		CartaoRequest dadosEmissao = new CartaoRequest(dadosPropostaCartao);
		solicitacaoCartao.emitirCartao(dadosEmissao);
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
