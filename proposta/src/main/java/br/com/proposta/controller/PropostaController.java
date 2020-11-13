package br.com.proposta.controller;

import java.net.URI;
import java.security.Principal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.proposta.component.ExecutorTransacao;
import br.com.proposta.dto.PropostaDTO;
import br.com.proposta.dto.enums.StatusAvaliacaoProposta;
import br.com.proposta.model.Proposta;
import br.com.proposta.service.CartaoServices;
import br.com.proposta.service.PropostaServices;
import io.opentracing.Span;
import io.opentracing.Tracer;

//Contagem de Pontos - TOTAL:7
//1 - ExecutorTransacao
//1 - PropostaDTO
//1 - Proposta
//1 - PropostaServices
//1 - StatusAvaliacaoProposta
//1 - CartaoServices
//1 - If

@RestController
public class PropostaController {
	
	@Autowired
	private PropostaServices propostaServices;
	
	@Autowired
	private CartaoServices cartaoServices;
	
	@Autowired
	private ExecutorTransacao executorTransacao;
	
	@Autowired
	private Tracer tracer;
	
	private Logger logger = LoggerFactory.getLogger(PropostaController.class);
	

	@PostMapping(value = "/v1/propostas")
	public ResponseEntity<?> criaProposta(@RequestBody @Valid PropostaDTO propostadto, UriComponentsBuilder builder, Principal principal) {
		Span activeSpan = tracer.activeSpan();
		activeSpan.setBaggageItem("Proposta.Email", propostadto.getEmail());
		activeSpan.setTag("Proposta.Email", propostadto.getEmail());
		
		if(propostaServices.validaDocumentoIgualParaProposta(propostadto) == false) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		Proposta novaProposta = propostadto.toModel();
		novaProposta.setIdKeyclock(principal.getName());
		executorTransacao.salvaEComita(novaProposta);
		logger.info("Salvo nova proposta - Antes da consulta de avalidação{}", novaProposta);
		
		StatusAvaliacaoProposta avaliacao = propostaServices.executaAvaliacao(novaProposta);
		novaProposta.setStatusAvaliacao(avaliacao);
		executorTransacao.atualizaEComita(novaProposta);
		logger.info("Salvo roposta - Depois da consulta de avalidação{}", novaProposta);
		
		cartaoServices.criaCartao(novaProposta);
		
		URI enderecoConsulta = builder.path("/v1/propostas/{id}").build(novaProposta.getId());
		
		return ResponseEntity.created(enderecoConsulta).build();
	}
}