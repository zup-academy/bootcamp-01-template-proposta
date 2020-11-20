package br.com.zup.nossocartao.cartaobloqueado.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.cartaobloqueado.service.CartaoService;
import br.com.zup.nossocartao.validador.ValidadorIdCartao;

//8
@RestController
public class CartaoController {

	// 1
	private final CartaoService cartaoService;

	// 1
	private final ValidadorIdCartao validaIdCartao;

	private final HttpServletRequest httpRequest;

	private final Logger logger = LoggerFactory.getLogger(CartaoController.class);

	public CartaoController(CartaoService cartaoService, ValidadorIdCartao validaIdCartao, HttpServletRequest request) {
		this.cartaoService = cartaoService;
		this.validaIdCartao = validaIdCartao;
		this.httpRequest = request;
	}

	// 1
	@PatchMapping(value = "/cartao/{id}/bloqueado")
	public ResponseEntity<?> bloquearCartao(@PathVariable("id") @NotBlank String idCartao,
			@RequestHeader("User-Agent") String userAgent, UriComponentsBuilder builder) {

		// 2
		if (validaIdCartao.idCartaoNaoExiste(idCartao)) {
			logger.warn("Este id do cartão não foi encontrado={}", idCartao);
			ResponseEntity.notFound();
		}

		// 1
		Optional<Long> bloquearCartao = cartaoService.bloquearCartao(idCartao, httpRequest.getRemoteAddr(), userAgent);

		logger.info("Cartão bloqueado");

		// 1
		UriComponents uriComponents = builder.path("/cartao/{id}/bloqueado").buildAndExpand(bloquearCartao.get());

		// 1
		return ResponseEntity.created(uriComponents.toUri()).build();

	}

}
