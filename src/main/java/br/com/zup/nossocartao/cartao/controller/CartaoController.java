package br.com.zup.nossocartao.cartao.controller;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.cartao.CartaoBloqueado;
import br.com.zup.nossocartao.cartao.service.CartaoService;
import br.com.zup.nossocartao.integracao.cartao.CartaoResponse;
import br.com.zup.nossocartao.integracao.cartao.SolicitacaoCartaoFeignClient;

@RestController
public class CartaoController {

	private final CartaoService cartaoService;

	private final SolicitacaoCartaoFeignClient cartaoFeingClient;

	public CartaoController(CartaoService cartaoService, SolicitacaoCartaoFeignClient cartaoFeingClient) {
		this.cartaoService = cartaoService;
		this.cartaoFeingClient = cartaoFeingClient;
	}

	@PatchMapping(value = "cartao/{id}/bloqueado")
	public ResponseEntity<?> bloquearCartao(@PathVariable("id") @NotBlank String idCartao, UriComponentsBuilder builder,
			HttpServletRequest request) {

		ResponseEntity<CartaoResponse> buscarDadosCartaoPorIdCartao = cartaoFeingClient
				.buscarDadosCartaoPorIdCartao(idCartao);

		if (buscarDadosCartaoPorIdCartao.getStatusCode() != HttpStatus.OK) {
			return ResponseEntity.notFound().build();

		}

		if (!buscarDadosCartaoPorIdCartao.getBody().getBloqueios().isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		String enderecoIp = request.getHeader("X-FORWARDED-FOR");

		if (isEmpty(enderecoIp)) {
			enderecoIp = request.getRemoteAddr();
		}

		String userAgent = request.getHeader("User-Agent");

		CartaoBloqueado dadosCartaoBloqueado = new CartaoBloqueado(idCartao, enderecoIp, userAgent);

		Optional<Long> bloquearCartao = cartaoService.bloquearCartao(dadosCartaoBloqueado);

		return ResponseEntity.ok(bloquearCartao.get());

	}

}
