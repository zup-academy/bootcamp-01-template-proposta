package br.com.zup.nossocartao.cartaobloqueado.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.cartaobloqueado.service.CartaoService;
import br.com.zup.nossocartao.validador.ValidadorIdCartao;

@RestController
public class CartaoController {

	private final CartaoService cartaoService;

	private final ValidadorIdCartao validaIdCartao;

	private final HttpServletRequest httpRequest;

	public CartaoController(CartaoService cartaoService, ValidadorIdCartao validaIdCartao, HttpServletRequest request) {
		this.cartaoService = cartaoService;
		this.validaIdCartao = validaIdCartao;
		this.httpRequest = request;
	}

	@PatchMapping(value = "cartao/{id}/bloqueado")
	public ResponseEntity<?> bloquearCartao(@PathVariable("id") @NotBlank String idCartao,
			@RequestHeader("User-Agent") String userAgent, UriComponentsBuilder builder) {

		if (validaIdCartao.idCartaoNaoExiste(idCartao)) {
			ResponseEntity.notFound();
		}

		Optional<Long> bloquearCartao = cartaoService.bloquearCartao(idCartao, httpRequest.getRemoteAddr(), userAgent);

		return ResponseEntity.ok(bloquearCartao.get());

	}

}
