package br.com.zup.nossocartao.biometria.controller;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.util.StringUtils.isEmpty;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.biometria.service.BiometriaService;

@RestController
public class BiometriaController {

	private final BiometriaService biometriaService;

	public BiometriaController(BiometriaService biometriaService) {
		this.biometriaService = biometriaService;
	}

	@PostMapping(value = "/biometria/{idCartao}")
	public ResponseEntity<?> salvarBiometria(@PathVariable("idCartao") String idCartao,
			@RequestHeader("fingerPrint") String fingerPrint, UriComponentsBuilder builder) {

		if (isEmpty(fingerPrint)) {
			return badRequest().build();
		}

		Optional<Long> biometriaSalva = biometriaService.salvarBiometria(fingerPrint, idCartao);

		if (biometriaSalva.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		UriComponents uriComponents = builder.path("/biometria/{id}").buildAndExpand(biometriaSalva.get());

		return ResponseEntity.created(uriComponents.toUri()).build();
	}

}
