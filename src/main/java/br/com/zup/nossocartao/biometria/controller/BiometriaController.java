package br.com.zup.nossocartao.biometria.controller;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.biometria.service.BiometriaService;

//4
@RestController
public class BiometriaController {

	// 1
	private final BiometriaService biometriaService;

	private final Logger logger = LoggerFactory.getLogger(BiometriaController.class);

	public BiometriaController(BiometriaService biometriaService) {
		this.biometriaService = biometriaService;
	}

	// 1
	@PostMapping(value = "/biometria/{idCartao}")
	public ResponseEntity<?> salvarBiometria(@PathVariable("idCartao") String idCartao,
			@RequestHeader("fingerPrint") @NotBlank String fingerPrint, UriComponentsBuilder builder) {

		Optional<Long> biometriaSalva = biometriaService.salvarBiometria(fingerPrint, idCartao);

		if (biometriaSalva.isEmpty()) {
			logger.warn("não foi encontrado o id do cartão informado={}", idCartao);
			return ResponseEntity.notFound().build();
		}

		logger.info("proposta com id={} gerado", biometriaSalva.get());

		// 1
		UriComponents uriComponents = builder.path("/biometria/{id}").buildAndExpand(biometriaSalva.get());

		// 1
		return ResponseEntity.created(uriComponents.toUri()).build();
	}

}
