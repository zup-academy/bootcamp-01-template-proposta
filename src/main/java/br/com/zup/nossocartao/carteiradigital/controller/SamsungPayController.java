package br.com.zup.nossocartao.carteiradigital.controller;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.status;

import java.net.URI;
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

import br.com.zup.nossocartao.carteiradigital.service.SamsungPayService;
import br.com.zup.nossocartao.validador.ValidadorIdCartao;

//7 

@RestController
public class SamsungPayController {

	// 1
	private final SamsungPayService samsungPayService;

	// 1
	private final ValidadorIdCartao validaIdCartao;

	private final Logger logger = LoggerFactory.getLogger(SamsungPayController.class);

	public SamsungPayController(SamsungPayService samsungPayService, ValidadorIdCartao validaIdCartao) {
		this.samsungPayService = samsungPayService;
		this.validaIdCartao = validaIdCartao;
	};

	// 1
	@PostMapping(value = "/cartoes/{id}/samsungpay")
	public ResponseEntity<?> carteiraSamsung(@PathVariable("id") String id, @RequestHeader("email") @Email String email,
			UriComponentsBuilder builder) {

		// 2
		if (validaIdCartao.idCartaoNaoExiste(id)) {
			logger.warn("Não encontro cartão com esse número");
			return ResponseEntity.notFound().build();
		}

		Optional<Long> salvarSamsumgPay = samsungPayService.associarSamsumgPay(id, email);

		// 1
		if (salvarSamsumgPay.isEmpty()) {
			logger.warn("Não foi possivel salvar a entidade");
			return status(UNPROCESSABLE_ENTITY).build();
		}

		Long idSamsungPay = salvarSamsumgPay.get();

		UriComponents uriComponents = builder.path("/cartoes/{id}/samsungpay").buildAndExpand(idSamsungPay);

		URI uri = uriComponents.toUri();

		return ResponseEntity.created(uri).build();
	}
}
