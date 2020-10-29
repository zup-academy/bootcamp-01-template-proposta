package br.com.zup.nossocartao.proposta.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.proposta.service.PropostaService;

@RestController
public class PropostaController {

	private PropostaService propostaService;

	public PropostaController(PropostaService propostaService) {
		this.propostaService = propostaService;
	}

	@PostMapping(value = "/propostas")
	public ResponseEntity<?> novaProposta(@RequestBody @Valid NovaPropostaRequest dadosProposta,
			UriComponentsBuilder builder) {

		boolean verificaCpfCnpj = propostaService.verificaDocumento(dadosProposta.getCpfCnpj());

		if (verificaCpfCnpj) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		NovaPropostaResponse propostaSalva = propostaService.salvarProposta(dadosProposta);

		UriComponents uriComponents = builder.path("/propostas/{id}").buildAndExpand(propostaSalva.getId());

		return ResponseEntity.created(uriComponents.toUri()).body(propostaSalva);
	}
}