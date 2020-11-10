package br.com.proposta.controller;

import java.net.URI;
import java.security.Principal;

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
import br.com.proposta.dto.AssociarCateiraConsultaDTO;
import br.com.proposta.dto.AssociarPaypalDTO;
import br.com.proposta.externos.Cartoes;
import br.com.proposta.model.Cartao;
import br.com.proposta.model.CarteiraSamsung;
import br.com.proposta.repository.CartaoRepository;

//Contagem de Pontos - TOTAL:7
//1 - ExecutorTransacao
//1 - AssociarCateiraConsultaDTO
//1 - AssociarPaypalDTO
//1 - Cartao
//1 - CarteiraPaypal
//1 - CartaoRepository
//1 - if

@RestController
public class CarteiraSamsungController {

	@Autowired
	private ExecutorTransacao executorTransacao;
	
	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Autowired
	private Cartoes cartoes;
	
	private Logger logger = LoggerFactory.getLogger(CarteiraSamsungController.class);
	
	@PostMapping(value = "/v1/associa-samsung/{id}")
	public ResponseEntity<?> associarSamsung(@PathVariable("id") Long id, @RequestBody @Valid AssociarPaypalDTO associarPaypaldto,UriComponentsBuilder builder, Principal principal) {
		
		Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		logger.info("Cartão encontrado {}", cartao);
		logger.info("Id do Keyclock recebido {}", principal.getName());
		
		if (cartao.getProposta().verificaUsuario(principal.getName()) == false) {
			return new ResponseEntity<>("Proposta não pertence ao seu usuário",HttpStatus.FORBIDDEN);
		}
		
		ResponseEntity<String> respostaAssociarCarteira = cartoes.associarCarteira(cartao.getNumero(), new AssociarCateiraConsultaDTO(associarPaypaldto.getEmail(), "Samsung"));
		logger.info("Retorno Sistema de Cartões - Associar Carteira{}", respostaAssociarCarteira);
		
		CarteiraSamsung novaCarteiraSamsung = new CarteiraSamsung(associarPaypaldto.getEmail(), cartao);
		cartao.associarSamsung(novaCarteiraSamsung);
		executorTransacao.atualizaEComita(cartao);
		logger.info("Solicitação de nova Carteira Samsung Criada {}", novaCarteiraSamsung);
		logger.info("Cartão atualizado {}", cartao);
		
		URI enderecoConsulta = builder.path("/v1/propostas/{id}").build(cartao.getProposta().getId());
		return ResponseEntity.created(enderecoConsulta).build();
	}
}
