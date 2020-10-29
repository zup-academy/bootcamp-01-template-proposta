package br.com.proposta.controller;

import java.math.BigDecimal;
import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.proposta.component.ExecutorTransacao;
import br.com.proposta.dto.PropostaDTO;
import br.com.proposta.dto.enums.StatusAvaliacaoProposta;
import br.com.proposta.model.Cartao;
import br.com.proposta.model.Proposta;
import br.com.proposta.service.PropostaServices;

@RestController
public class PropostaController {
	
	@Autowired
	private PropostaServices propostaServices;
	
	@Autowired
	private ExecutorTransacao executorTransacao;
	
	private Logger logger = LoggerFactory.getLogger(PropostaController.class);
	

	@PostMapping(value = "/v1/propostas")
	public ResponseEntity<?> criaProposta(@RequestBody @Valid PropostaDTO propostadto, UriComponentsBuilder builder) {
		
		if(propostaServices.validaDocumentoIgualParaProposta(propostadto) == false) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		Proposta novaProposta = propostadto.toModel();
		executorTransacao.salvaEComita(novaProposta);
		logger.info("Salvo nova proposta - Antes da consulta de avalidação{}", novaProposta);
		
		StatusAvaliacaoProposta avaliacao = propostaServices.executaAvaliacao(novaProposta);
		novaProposta.setStatusAvaliacao(avaliacao);
		executorTransacao.atualizaEComita(novaProposta);
		logger.info("Salvo roposta - Depois da consulta de avalidação{}", novaProposta);
		
		Cartao cartaoCriado = propostaServices.criaCartao(novaProposta);
		executorTransacao.salvaEComita(cartaoCriado);
		logger.info("Cartao criado {}", cartaoCriado);
		
		URI enderecoConsulta = builder.path("/v1/propostas/{id}").build(novaProposta.getId());
		
		return ResponseEntity.created(enderecoConsulta).build();
	}

	@GetMapping(value = "/v1/teste2")
	public ResponseEntity<?> teste() {
		String email = "gabriel@gmail.com";
		String nome = "teste cartao";
		String endereco = "rua teste";
		BigDecimal salario = new BigDecimal(100.00);
		String documento = "83026485071";
		
		PropostaDTO propostadto = new PropostaDTO(email, nome, endereco, salario, documento);
		Proposta proposta = propostadto.toModel();
		
		System.out.println("------------ TESTE CONTROLLER CARTAO ----------------");
		System.out.println(proposta.toString());
		
		Cartao cartaoCriado = propostaServices.criaCartao(proposta);
		
		return new ResponseEntity<>(cartaoCriado,HttpStatus.OK);
	}
}