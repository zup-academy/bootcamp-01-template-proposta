package br.com.proposta.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.proposta.component.ExecutorTransacao;
import br.com.proposta.dto.BiometriaDTO;
import br.com.proposta.model.Biometria;
import br.com.proposta.model.Cartao;
import br.com.proposta.repository.CartaoRepository;

@RestController
public class BiometriaController {
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private ExecutorTransacao executorTransacao;
	
	private Logger logger = LoggerFactory.getLogger(BiometriaController.class);

	@PostMapping(value = "/v1/biometria/{id}")
	public ResponseEntity<?> criaProposta(@PathVariable("id") Long id,@RequestBody @Valid BiometriaDTO biometriadto, UriComponentsBuilder builder) {

		Biometria biometriaCriada = biometriadto.toModel();
		logger.info("Biometria criada {}", biometriaCriada);
		
		Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		cartao.adicionaBiometria(biometriaCriada);
		biometriaCriada.setCartao(cartao);
		executorTransacao.atualizaEComita(cartao);
		
		logger.info("Cartao Depois de Adicionar a Biometria {}", cartao);
		
		URI enderecoConsulta = builder.path("/v1/propostas/{id}").build(biometriaCriada.getId());
		return ResponseEntity.created(enderecoConsulta).build();
	}
}
