package br.com.zup.nossocartao.proposta.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.proposta.service.PropostaService;

//7
@RestController
public class PropostaController {

	// 1
	private PropostaService propostaService;

	private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

	public PropostaController(PropostaService propostaService) {
		this.propostaService = propostaService;
	}

	// 1
	@PostMapping(value = "/propostas")
	public ResponseEntity<?> novaProposta(@RequestBody @Valid NovaPropostaRequest dadosProposta,
			UriComponentsBuilder builder, HttpServletRequest request) {

		String cpfCnpj = dadosProposta.getCpfCnpj();

		boolean verificaCpfCnpj = propostaService.verificaDocumento(cpfCnpj);

		// 1
		if (verificaCpfCnpj) {

			logger.warn("Verificar se o cpf e o cnpj passado é valido");

			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		NovaPropostaResponse propostaSalva = propostaService.salvarProposta(dadosProposta);

		Long id = propostaSalva.getId();

		UriComponents uriComponents = builder.path("/propostas/{id}").buildAndExpand(id);

		logger.info("Proposta Salva");

		// 1
		return ResponseEntity.created(uriComponents.toUri()).body(propostaSalva);
	}

	// 1
	@GetMapping(value = "/propostas/{idProposta}")
	public ResponseEntity<?> acompanharProposta(@PathVariable("idProposta") Long idProposta) {

		boolean verificaId = propostaService.verificaId(idProposta);

		// 1
		if (!verificaId) {

			logger.warn("Caso o id seja diferente retorne não encontrado");

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		NovaPropostaResponse buscarPropostaPorId = propostaService.buscarPropostaPorId(idProposta);

		return ResponseEntity.ok(buscarPropostaPorId);

	}

}