package br.com.zup.nossocartao.recuperacaosenha.controller;

import static org.springframework.util.StringUtils.isEmpty;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.integracao.cartao.CartaoFeignClient;
import br.com.zup.nossocartao.integracao.cartao.CartaoResponse;
import br.com.zup.nossocartao.recuperacaosenha.service.RecuperarSenhaService;
import br.com.zup.nossocartao.viagem.service.AvisoViagemService;

//8
@RestController
public class RecupuraSenhaController {

	// 1
	private final RecuperarSenhaService recuperarSenhaService;

	// 1
	private final CartaoFeignClient cartaoFeignClient;

	private final Logger logger = LoggerFactory.getLogger(AvisoViagemService.class);

	public RecupuraSenhaController(RecuperarSenhaService recuperarSenhaService, CartaoFeignClient cartaoFeignClient) {
		this.recuperarSenhaService = recuperarSenhaService;
		this.cartaoFeignClient = cartaoFeignClient;
	}

	// 1
	@PostMapping(value = "/recuperarsenha/{idCartao}")
	public ResponseEntity<?> gerarNovaSenha(@PathVariable("idCartao") String idCartao, UriComponentsBuilder builder,
			HttpServletRequest request) {

		// 1
		ResponseEntity<CartaoResponse> buscarDadosCartaoPorIdCartao = cartaoFeignClient
				.buscarDadosCartaoPorIdCartao(idCartao);
		// 2
		if (buscarDadosCartaoPorIdCartao.getStatusCode() != HttpStatus.OK) {
			logger.warn("Erro ao comunicar com o sistema legado");
			return ResponseEntity.notFound().build();
		}

		String ipCliente = request.getHeader("X-FORWARDED-FOR");
		// 1
		if (isEmpty(ipCliente)) {
			logger.warn("Ip do cliente não pode ser vazio.");
			ipCliente = request.getRemoteAddr();
		}

		String userAgent = request.getHeader("User-Agent");

		Long recuperarSenha = recuperarSenhaService.recuperarSenha(idCartao, ipCliente, userAgent);

		UriComponents uriComponests = builder.path("/recuperarSenha/{idCartao}").buildAndExpand(recuperarSenha);

		// 1
		return ResponseEntity.created(uriComponests.toUri()).build();

	}
}
