package br.com.zup.nossocartao.proposta.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

	// @RolesAllowed("administrador")
	@PostMapping(value = "/propostas")
	public ResponseEntity<?> novaProposta(@RequestHeader String Authorization,
			@RequestBody @Valid NovaPropostaRequest dadosProposta, UriComponentsBuilder builder,
			HttpServletRequest request) {

		boolean verificaCpfCnpj = propostaService.verificaDocumento(dadosProposta.getCpfCnpj());

		if (verificaCpfCnpj) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		NovaPropostaResponse propostaSalva = propostaService.salvarProposta(dadosProposta);

		UriComponents uriComponents = builder.path("/propostas/{id}").buildAndExpand(propostaSalva.getId());

		return ResponseEntity.created(uriComponents.toUri()).body(propostaSalva);
	}

	@GetMapping(value = "/propostas/{idProposta}")
	public ResponseEntity<?> acompanharProposta(@PathVariable("idProposta") Long idProposta) {
		boolean verificaId = propostaService.verificaId(idProposta);

		if (!verificaId) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		NovaPropostaResponse buscarPropostaPorId = propostaService.buscarPropostaPorId(idProposta);

		return ResponseEntity.ok(buscarPropostaPorId);

	}

}