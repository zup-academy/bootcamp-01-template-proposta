package br.com.proposta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.proposta.model.Proposta;
import br.com.proposta.repository.PropostaRepository;

@RestController
public class DetalhePropostaController {
	
	@Autowired
	private PropostaRepository propostaRepository;
	
	@GetMapping(value = "/v1/propostas/{id}")
	public ResponseEntity<?> buscaDetalhesProposta(@PathVariable("id") Long id) {	
		Proposta proposta = propostaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return new ResponseEntity<>(proposta,HttpStatus.OK);
	}
}
