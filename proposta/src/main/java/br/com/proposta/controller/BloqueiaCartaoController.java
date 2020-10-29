package br.com.proposta.controller;

import java.net.URI;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.proposta.component.ExecutorTransacao;
import br.com.proposta.externos.Cartoes;
import br.com.proposta.model.Cartao;
import br.com.proposta.repository.CartaoRepository;

@RestController
public class BloqueiaCartaoController {

	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private ExecutorTransacao executorTransacao;
	
	@Autowired
	private Cartoes cartoes;
	
	private Logger logger = LoggerFactory.getLogger(BloqueiaCartaoController.class);

	@PostMapping(value = "/v1/bloqueio-cartao/{id}")
	public ResponseEntity<?> criaProposta(@PathVariable("id") Long id, UriComponentsBuilder builder,HttpServletRequest httpRequest, @RequestHeader HttpHeaders headers) {

		logger.info("Tentativa bloqueio cartao [{}] vindo do user-agent {}", id,headers.get(HttpHeaders.USER_AGENT));
		String ip = httpRequest.getRemoteAddr();
		String userAgent = headers.get(HttpHeaders.USER_AGENT).get(0);
		
		Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		logger.info("Cartão encontrado {}", cartao);
		
		ResponseEntity<String> resposta = cartoes.bloqueiaCartao(cartao.getNumero(),(Map.of("sistemaResponsavel", "BootCamp - Proposta")));
		
		cartao.bloqueia(userAgent, ip);
		logger.info("Cartão depois do bloqueio {}", cartao);
		
		executorTransacao.atualizaEComita(cartao);
		
		URI enderecoConsulta = builder.path("/v1/propostas/{id}").build(cartao.getProposta().getId());
		return ResponseEntity.created(enderecoConsulta).build();
	}
	
	
	@PostMapping(value = "/v1/bloqueio-cartao/teste")
	public ResponseEntity<?> testeBloqueio(){
		logger.info("TESTE BLOQUEIO {}");
		
		String numeroCartao = "caf356c2-850d-40bb-9d0d-020669b825f3"; 
		String sistemaResponsavel = "teste";
		
		ResponseEntity<String> resposta = cartoes.bloqueiaCartao(numeroCartao,(Map.of("sistemaResponsavel", sistemaResponsavel)));
		String resultado = resposta.getBody();
		
		return new ResponseEntity<>(resultado,HttpStatus.OK);
	}
}
