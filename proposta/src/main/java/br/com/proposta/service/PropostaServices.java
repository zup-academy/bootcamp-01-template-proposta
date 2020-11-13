package br.com.proposta.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.proposta.dto.DocumentoConsultaDTO;
import br.com.proposta.dto.DocumentoRetornoDTO;
import br.com.proposta.dto.PropostaDTO;
import br.com.proposta.dto.enums.RespostaStatusAvaliacao;
import br.com.proposta.dto.enums.StatusAvaliacaoProposta;
import br.com.proposta.externos.Integracoes;
import br.com.proposta.model.Proposta;

//Contagem de Pontos - TOTAL:6
//1 - DocumentoConsultaDTO
//1 - DocumentoRetornoDTO
//1 - PropostaDTO
//1 - RespostaStatusAvaliacao
//1 - Integracoes
//1 - Proposta


@Service
@Validated
public class PropostaServices {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private Integracoes integracoes;
	
	public boolean validaDocumentoIgualParaProposta(PropostaDTO propostadto) {
		Query query = manager.createQuery("select p.documento from Proposta p where p.documento = :documento");
		query.setParameter("documento", propostadto.getDocumento());
		return query.getResultList().isEmpty();
	}
	
	public StatusAvaliacaoProposta executaAvaliacao(@NotNull @Validated Proposta proposta) {
		DocumentoRetornoDTO resultadoAvaliacao = integracoes.avalia(new DocumentoConsultaDTO(proposta));
		return RespostaStatusAvaliacao.valueOf(resultadoAvaliacao.getResultadoSolicitacao()).getStatusAvaliacao();
	}	
}
