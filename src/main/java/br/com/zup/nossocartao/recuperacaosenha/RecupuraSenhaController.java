package br.com.zup.nossocartao.recuperacaosenha;

import static org.springframework.util.StringUtils.isEmpty;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.integracao.cartao.CartaoResponse;
import br.com.zup.nossocartao.integracao.cartao.CartaoFeignClient;

@RestController
public class RecupuraSenhaController {

	private final RecuperarSenhaService recuperarSenhaService;

	private final CartaoFeignClient cartaoFeignClient;

	public RecupuraSenhaController(RecuperarSenhaService recuperarSenhaService,
			CartaoFeignClient cartaoFeignClient) {
		this.recuperarSenhaService = recuperarSenhaService;
		this.cartaoFeignClient = cartaoFeignClient;
	}

	@PostMapping(value = "/recuperarsenha/{idCartao}")
	public ResponseEntity<?> gerarNovaSenha(@PathVariable("idCartao") String idCartao, UriComponentsBuilder builder,
			HttpServletRequest request) {

		ResponseEntity<CartaoResponse> buscarDadosCartaoPorIdCartao = cartaoFeignClient
				.buscarDadosCartaoPorIdCartao(idCartao);
		if (buscarDadosCartaoPorIdCartao.getStatusCode() != HttpStatus.OK) {
			return ResponseEntity.notFound().build();
		}

		String ipCliente = request.getHeader("X-FORWARDED-FOR");

		if (isEmpty(ipCliente)) {
			ipCliente = request.getRemoteAddr();
		}

		String userAgent = request.getHeader("User-Agent");

		Long recuperarSenha = recuperarSenhaService.recuperarSenha(idCartao, ipCliente, userAgent);

		UriComponents uriComponests = builder.path("/recuperarSenha/{idCartao}").buildAndExpand(recuperarSenha);

		return ResponseEntity.created(uriComponests.toUri()).build();

	}
}
