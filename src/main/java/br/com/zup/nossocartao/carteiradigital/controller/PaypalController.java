package br.com.zup.nossocartao.carteiradigital.controller;

import java.util.Optional;

import javax.validation.constraints.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.nossocartao.carteiradigital.service.PaypalService;
import br.com.zup.nossocartao.validador.ValidadorIdCartao;

//7
@RestController
public class PaypalController {

	// 1
	private final PaypalService paypalService;

	// 1
	private final ValidadorIdCartao validaIdCartao;

	private final Logger logger = LoggerFactory.getLogger(PaypalController.class);

	public PaypalController(PaypalService paypalService, ValidadorIdCartao validaIdCartao) {
		this.paypalService = paypalService;
		this.validaIdCartao = validaIdCartao;
	}

	// 1
	@PostMapping(value = "/cartoes/{id}/paypal")
	public ResponseEntity<?> carteiraPaypal(@PathVariable("id") String id, @RequestHeader("email") @Email String email,
			UriComponentsBuilder builder) {

		// 2
		if (validaIdCartao.idCartaoNaoExiste(id)) {
			logger.warn("Não encontro cartão com esse número");
			return ResponseEntity.notFound().build();
		}

		Optional<Long> salvarPaypal = paypalService.associarPaypal(id, email);

		// 1
		UriComponents uriComponests = builder.path("/cartoes/{id}/paypal").buildAndExpand(salvarPaypal.get());

		// 1
		return ResponseEntity.created(uriComponests.toUri()).build();

	}

}
