package br.com.proposta.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import br.com.proposta.dto.CartaoConsultaDTO;
import br.com.proposta.dto.CartaoRetornoDTO;
import br.com.proposta.dto.DocumentoConsultaDTO;
import br.com.proposta.dto.DocumentoRetornoDTO;
import br.com.proposta.dto.PropostaDTO;
import br.com.proposta.dto.enums.RespostaStatusAvaliacao;
import br.com.proposta.dto.enums.StatusAvaliacaoProposta;
import br.com.proposta.externos.Integracoes;
import br.com.proposta.model.Cartao;
import br.com.proposta.model.Proposta;

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
		System.out.println(resultadoAvaliacao.toString());
		return RespostaStatusAvaliacao.valueOf(resultadoAvaliacao.getResultadoSolicitacao()).getStatusAvaliacao();
	}
	
	public Cartao criaCartao (Proposta proposta) {
		RestTemplate restTemplate = new RestTemplate();
		
		CartaoConsultaDTO request = new CartaoConsultaDTO(proposta);	
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8888/api/cartoes",request, String.class);
		String retorno = response.getHeaders().getLocation().toString();
		String [] resultado = retorno.split("/");
		
		ResponseEntity<CartaoRetornoDTO> responseGet = restTemplate.getForEntity("http://localhost:8888/api/cartoes/"+resultado[4], CartaoRetornoDTO.class);
		
		System.out.println(responseGet.getBody());
		Cartao cartaoCriado = responseGet.getBody().toModel();
		System.out.println(cartaoCriado.toString());
		
		return cartaoCriado;
	}
	
	
}
