package br.com.zup.nossocartao.viagem.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.validador.ValidadorIdCartao;
import br.com.zup.nossocartao.viagem.service.AvisoViagemService;

//6
@RestController
public class AvisoViagemController {

	// 1
	private final AvisoViagemService avisoViagemService;

	// 1
	private final ValidadorIdCartao validaIdCartao;

	private final HttpServletRequest hpptRequest;

	private final Logger logger = LoggerFactory.getLogger(AvisoViagemService.class);

	public AvisoViagemController(AvisoViagemService avisoViagemService, ValidadorIdCartao validaIdCartao,
			HttpServletRequest hpptRequest) {
		this.avisoViagemService = avisoViagemService;
		this.validaIdCartao = validaIdCartao;
		this.hpptRequest = hpptRequest;
	}

	// 1
	@PostMapping(value = "/avisoviagem/{idCartao}")
	public ResponseEntity<?> avisarViagem(@PathVariable("idCartao") String idCartao,
			@RequestBody @Valid AvisoViagemRequest request, @RequestHeader("User-Agent") String userAgent,
			UriComponentsBuilder builder) {

		// 1
		if (validaIdCartao.idCartaoNaoExiste(idCartao)) {
			logger.warn("Dados do cartão estão incorretos");
			ResponseEntity.notFound();
		}

		// 1
		request.setDadosClient(hpptRequest.getRemoteAddr(), userAgent, idCartao);

		Optional<Long> IdAvisarViagem = avisoViagemService.avisarViagem(request);

		Long idAvisarViagem = IdAvisarViagem.get();
		UriComponents uriComponents = builder.path("/avisoviagem/{idCartao}").buildAndExpand(idAvisarViagem);

		logger.info("Aviso de viagem criado com sucesso");
		// 1
		return ResponseEntity.created(uriComponents.toUri()).build();
	}
}
