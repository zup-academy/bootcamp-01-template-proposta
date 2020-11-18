package br.com.zup.nossocartao.proposta.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.zup.nossocartao.integracao.analise.SolicitacaoAnalise;
import br.com.zup.nossocartao.integracao.analise.SolicitacaoAnaliseRequest;
import br.com.zup.nossocartao.integracao.analise.SolicitacaoAnaliseResponse;
import br.com.zup.nossocartao.integracao.cartao.CartaoFeignClient;
import br.com.zup.nossocartao.integracao.cartao.CartaoRequest;
import br.com.zup.nossocartao.integracao.cartao.CartaoResponse;
import br.com.zup.nossocartao.proposta.Proposta;
import br.com.zup.nossocartao.proposta.controller.NovaPropostaRequest;
import br.com.zup.nossocartao.proposta.controller.NovaPropostaResponse;
import br.com.zup.nossocartao.proposta.repository.PropostaRepository;

@Service
public class PropostaService {

	private PropostaRepository propostaRepository;

	private SolicitacaoAnalise solicitacaoAnalise;

	private CartaoFeignClient solicitacaoCartao;

	@Value("${seguranca.criptografia.chave}")
	private String chaveCriptografia;

	public PropostaService(PropostaRepository propostaRepository, SolicitacaoAnalise solicitacaoAnalise,
			CartaoFeignClient solicitacaoCartao) {
		this.propostaRepository = propostaRepository;
		this.solicitacaoAnalise = solicitacaoAnalise;
		this.solicitacaoCartao = solicitacaoCartao;
	}

	public NovaPropostaResponse salvarProposta(NovaPropostaRequest dadosRequisitados) {
		Proposta novaProposta = dadosRequisitados.gerarProposta();

		novaProposta.criptografar(chaveCriptografia);

		Proposta propostaSalva = propostaRepository.save(novaProposta);

		avaliacaoFinanceira(propostaSalva);

		emitirCartao(propostaSalva);

		Proposta anexarDadosCartao = anexarDadosCartao(propostaSalva);

		NovaPropostaResponse dadosPropostaResponse = new NovaPropostaResponse(anexarDadosCartao);

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

	private Proposta anexarDadosCartao(Proposta dadosProposta) {
		CartaoResponse dadosCartao = solicitacaoCartao.buscarDadosCartaoPorIdProposta(dadosProposta.getId().toString());
		dadosProposta.setNumeroCartao(dadosCartao.getId());
		return propostaRepository.save(dadosProposta);
	}

	public boolean verificaDocumento(String cpfCnpj) {
		boolean exiteCpfCnpj = propostaRepository.existsByCpfCnpj(cpfCnpj);
		return exiteCpfCnpj;
	}

	public boolean verificaId(Long idProposta) {
		boolean existeId = propostaRepository.existsById(idProposta);
		return existeId;
	}

	public NovaPropostaResponse buscarPropostaPorId(Long idProposta) {
		Optional<Proposta> proposta = propostaRepository.findById(idProposta);
		NovaPropostaResponse converterDados = new NovaPropostaResponse(proposta.get());
		return converterDados;

	}
}
