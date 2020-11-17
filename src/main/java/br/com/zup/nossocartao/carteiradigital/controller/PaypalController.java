package br.com.zup.nossocartao.carteiradigital.controller;

import java.util.Optional;

import javax.validation.constraints.Email;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.carteiradigital.service.PaypalService;
import br.com.zup.nossocartao.validador.ValidadorIdCartao;

@RestController
public class PaypalController {

	private final PaypalService paypalService;

	private final ValidadorIdCartao validaIdCartao;

	public PaypalController(PaypalService paypalService, ValidadorIdCartao validaIdCartao) {
		this.paypalService = paypalService;
		this.validaIdCartao = validaIdCartao;
	}

	@PostMapping(value = "/cartoes/{id}/carteiras")
	public ResponseEntity<?> CarteiraPaypal(@PathVariable("id") String id, @RequestHeader("email") @Email String email,
			UriComponentsBuilder builder) {

		if (validaIdCartao.idCartaoNaoExiste(id)) {
			ResponseEntity.notFound();
		}

		Optional<Long> salvarPaypal = paypalService.associarPaypal(id, email);

		UriComponents uriComponests = builder.path("/cartoes/{id}/carteiras").buildAndExpand(salvarPaypal.get());

		return ResponseEntity.created(uriComponests.toUri()).build();

	}
}
