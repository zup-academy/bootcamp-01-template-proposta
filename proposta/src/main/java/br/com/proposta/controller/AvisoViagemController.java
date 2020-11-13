package br.com.proposta.controller;

import java.net.URI;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.proposta.dto.AvisoViagemDTO;
import br.com.proposta.model.Cartao;
import br.com.proposta.repository.CartaoRepository;
import br.com.proposta.service.AvisoViagemServices;

//Contagem de Pontos - TOTAL:5
//1 - AvisoViagemDTO
//1 - CartaoRepository
//1 - Cartao
//1 - AvisoViagemServices
//1 - If

@RestController
public class AvisoViagemController {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private AvisoViagemServices avisoViagemservices;
	
	private Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);
	
	@PostMapping(value = "/v1/aviso-viagem/{id}")
	public ResponseEntity<?> criarAvisoViagem(@PathVariable("id") Long id, @RequestBody @Valid AvisoViagemDTO avisoViagemDTO,UriComponentsBuilder builder,HttpServletRequest httpRequest, @RequestHeader HttpHeaders headers, Principal principal) {

		logger.info("Tentativa de Cadastro de Aviso Viagem do cartao [{}] vindo do user-agent {}", id,headers.get(HttpHeaders.USER_AGENT));
		String ip = httpRequest.getRemoteAddr();
		String userAgent = headers.get(HttpHeaders.USER_AGENT).get(0);
		
		Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		logger.info("Cartão encontrado {}", cartao);
		
		if (cartao.getProposta().verificaUsuario(principal.getName()) == false) {
			return new ResponseEntity<>("Proposta não pertence ao seu usuário",HttpStatus.FORBIDDEN);
		}
			
		avisoViagemservices.enviarAvisoViagem(cartao, avisoViagemDTO, userAgent, ip);
		
		URI enderecoConsulta = builder.path("/v1/propostas/{id}").build(cartao.getProposta().getId());
		return ResponseEntity.created(enderecoConsulta).build();
	}
}
