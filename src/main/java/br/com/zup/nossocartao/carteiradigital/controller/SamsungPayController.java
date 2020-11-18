package br.com.zup.nossocartao.carteiradigital.controller;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.status;

import java.util.Optional;

import javax.validation.constraints.Email;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.carteiradigital.service.SamsungPayService;
import br.com.zup.nossocartao.validador.ValidadorIdCartao;

@RestController
public class SamsungPayController {

	private final SamsungPayService samsungPayService;

	private final ValidadorIdCartao validaIdCartao;

	public SamsungPayController(SamsungPayService samsungPayService, ValidadorIdCartao validaIdCartao) {
		this.samsungPayService = samsungPayService;
		this.validaIdCartao = validaIdCartao;
	};

	@PostMapping(value = "/cartoes/{id}/samsungpay")
	public ResponseEntity<?> carteiraSamsung(@PathVariable("id") String id, @RequestHeader("email") @Email String email,
			UriComponentsBuilder builder) {

		if (validaIdCartao.idCartaoNaoExiste(id)) {
			return ResponseEntity.notFound().build();
		}

		Optional<Long> salvarSamsumgPay = samsungPayService.associarSamsumgPay(id, email);

		if (salvarSamsumgPay.isEmpty()) {
			return status(UNPROCESSABLE_ENTITY).build();
		}

		UriComponents uriComponents = builder.path("/cartoes/{id}/samsungpay").buildAndExpand(salvarSamsumgPay.get());

		return ResponseEntity.created(uriComponents.toUri()).build();
	}
}
