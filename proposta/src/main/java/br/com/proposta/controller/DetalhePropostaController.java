package br.com.proposta.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.proposta.model.Proposta;
import br.com.proposta.repository.PropostaRepository;
import io.opentracing.Span;
import io.opentracing.Tracer;

//Contagem de Pontos - TOTAL:3
//1 - Proposta
//1 - PropostaRepository
//1 - If


@RestController
public class DetalhePropostaController {
	
	@Autowired
	private PropostaRepository propostaRepository;
	
	@Autowired
	private Tracer tracer;
	
	private static final Logger logger = LoggerFactory.getLogger(DetalhePropostaController.class);
	
	@GetMapping(value = "/v1/propostas/{id}")
	public ResponseEntity<?> buscaDetalhesProposta(@PathVariable("id") Long id, Principal principal) {	
		Span activeSpan = tracer.activeSpan();
		Proposta proposta = propostaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		logger.info("Id do Keyclock recebido {}", principal.getName());
		
		if (proposta.verificaUsuario(principal.getName()) == false) {
			return new ResponseEntity<>("Proposta não pertence ao seu usuário",HttpStatus.FORBIDDEN);
		}
		
		activeSpan.setBaggageItem("Proposta.Email", proposta.getEmail());
		activeSpan.setTag("Proposta.Email", proposta.getEmail());
		
		return new ResponseEntity<>(proposta,HttpStatus.OK);
	}
}
