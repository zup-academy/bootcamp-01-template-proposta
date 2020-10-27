package br.com.zup.nossocartao.proposta;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class PropostaController {

	@PersistenceContext
	private EntityManager bancoDados;

	@PostMapping(value = "/propostas")
	@Transactional
	public ResponseEntity<?> novaProposta(@RequestBody @Valid NovaPropostaRequest dadosProposta,
			UriComponentsBuilder builder) {

		Proposta novaProposta = dadosProposta.gerarProposta();
		bancoDados.persist(novaProposta);

		UriComponents uriComponents = builder.path("/propostas/{id}").buildAndExpand(novaProposta.getId());

		return ResponseEntity.created(uriComponents.toUri()).build();
	}
}
