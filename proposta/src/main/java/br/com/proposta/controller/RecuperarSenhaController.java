package br.com.proposta.controller;

import java.net.URI;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.proposta.component.ExecutorTransacao;
import br.com.proposta.model.Cartao;
import br.com.proposta.model.SolicitacaoRecuperarSenha;
import br.com.proposta.repository.CartaoRepository;

//Contagem de Pontos - TOTAL:4
//1 - ExecutorTransacao
//1 - Cartao
//1 - SolicitacaoRecuperarSenha
//1 - CartaoRepository
//1 - If

@RestController
public class RecuperarSenhaController {

	@Autowired
	private ExecutorTransacao executorTransacao;
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	private Logger logger = LoggerFactory.getLogger(RecuperarSenhaController.class);
	
	@PostMapping(value = "/v1/recuperar-senha/{id}")
	public ResponseEntity<?> recuperarSenha(@PathVariable("id") Long id, UriComponentsBuilder builder,HttpServletRequest httpRequest, @RequestHeader HttpHeaders headers, Principal principal) {

		logger.info("Tentativa de Troca de senha do cartao [{}] vindo do user-agent {}", id,headers.get(HttpHeaders.USER_AGENT));
		String ip = httpRequest.getRemoteAddr();
		String userAgent = headers.get(HttpHeaders.USER_AGENT).get(0);
		
		Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		logger.info("Cartão encontrado {}", cartao);
		logger.info("Id do Keyclock recebido {}", principal.getName());
		
		if (cartao.getProposta().verificaUsuario(principal.getName()) == false) {
			return new ResponseEntity<>("Proposta não pertence ao seu usuário",HttpStatus.FORBIDDEN);
		}
		
		SolicitacaoRecuperarSenha novaSolicitacaoRecuperarSenha = new SolicitacaoRecuperarSenha(userAgent, ip, cartao);
		executorTransacao.salvaEComita(novaSolicitacaoRecuperarSenha);
		logger.info("Solicitação de Troca de Senha Criada {}", novaSolicitacaoRecuperarSenha);
		
		URI enderecoConsulta = builder.path("/v1/propostas/{id}").build(cartao.getProposta().getId());
		return ResponseEntity.created(enderecoConsulta).build();
	}
}
