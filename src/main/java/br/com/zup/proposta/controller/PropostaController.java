package br.com.zup.proposta.controller;

import br.com.zup.proposta.dto.NovaPropostaDtoRequest;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.validations.DocumentoIgualValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    private EntityManager entityManager;
    private DocumentoIgualValidator documentoIgualValidator;

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    public PropostaController(EntityManager entityManager, DocumentoIgualValidator documentoIgualValidator) {
        this.entityManager = entityManager;
        this.documentoIgualValidator = documentoIgualValidator;
    }

    @PostMapping
    @Transactional
    public ResponseEntity novaProposta(@RequestBody @Valid NovaPropostaDtoRequest request,
                                       UriComponentsBuilder builder){

        //é necessário descrever o erro aqui?
        if(documentoIgualValidator.existe(request))
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Documento inválido!");

        Proposta novaProposta = request.toProposta();

        logger.info("Proposta: documento= {}", request.getDocumento());

        entityManager.persist(novaProposta);

        return ResponseEntity.created(builder.path("/api/propostas/{id}")
                .buildAndExpand(novaProposta.getId())
                .toUri())
                .build();
    }


}
