package br.com.itau.cartaobrancoproposta.controller;

import br.com.itau.cartaobrancoproposta.error.ApiErrorException;
import br.com.itau.cartaobrancoproposta.model.Proposta;
import br.com.itau.cartaobrancoproposta.model.PropostaRequest;
import br.com.itau.cartaobrancoproposta.validator.VerificaPropostaMesmoSolicitante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class PropostaController {

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    private final EntityManager entityManager;

    private final VerificaPropostaMesmoSolicitante verificaProposta;

    public PropostaController(EntityManager entityManager, VerificaPropostaMesmoSolicitante verificaPropostaMesmoSolicitante) {
        this.entityManager = entityManager;
        this.verificaProposta = verificaPropostaMesmoSolicitante;
    }

    @PostMapping("/v1/proposta")
    @Transactional
    public ResponseEntity<?> criaProposta(@RequestBody @Valid PropostaRequest novaPropostaRequest, UriComponentsBuilder builder) {
        if (!verificaProposta.estaValido(novaPropostaRequest.getDocumento())) {
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Documento (" + novaPropostaRequest.getDocumento() + ") já está cadastrado");
        }

        Proposta proposta = novaPropostaRequest.toModel();
        entityManager.persist(proposta);
        logger.info("Proposta id={} documento={} salário={} criada com sucesso!", proposta.getId(), proposta.getDocumento(), proposta.getSalario());
        URI enderecoConsulta = builder.path("/v1/proposta/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(enderecoConsulta).build();
    }
}
