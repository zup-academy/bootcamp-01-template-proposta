package br.com.zup.nossocartao.viagem.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

@RestController
public class AvisoViagemController {

	private final AvisoViagemService avisoViagemService;

	private final ValidadorIdCartao validaIdCartao;

	private final HttpServletRequest hpptRequest;

	public AvisoViagemController(AvisoViagemService avisoViagemService, ValidadorIdCartao validaIdCartao,
			HttpServletRequest hpptRequest) {
		this.avisoViagemService = avisoViagemService;
		this.validaIdCartao = validaIdCartao;
		this.hpptRequest = hpptRequest;
	}

	@PostMapping(value = "/avisoviagem/{idCartao}")
	public ResponseEntity<?> avisarViagem(@PathVariable("idCartao") String idCartao,
			@RequestBody @Valid AvisoViagemRequest request, @RequestHeader("User-Agent") String userAgent,
			UriComponentsBuilder builder) {

		if (validaIdCartao.idCartaoNaoExiste(idCartao)) {
			ResponseEntity.notFound();
		}

		request.setDadosClient(hpptRequest.getRemoteAddr(), userAgent, idCartao);

		Long IdAvisarViagem = avisoViagemService.avisarViagem(request);

		UriComponents uriComponents = builder.path("/avisoviagem/{idCartao}").buildAndExpand(IdAvisarViagem);

		return ResponseEntity.created(uriComponents.toUri()).build();
	}
}
