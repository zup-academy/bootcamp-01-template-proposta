package br.com.zup.proposta.controller;

import br.com.zup.proposta.dto.AvaliaProposta;
import br.com.zup.proposta.dto.NovaPropostaDtoRequest;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.StatusAvaliacaoProposta;
import br.com.zup.proposta.validations.DocumentoIgualValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    private DocumentoIgualValidator documentoIgualValidator; //1

    private AvaliaProposta avaliaProposta; //2

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    public PropostaController(EntityManager entityManager,
                              DocumentoIgualValidator documentoIgualValidator,
                              AvaliaProposta avaliaProposta) {
        this.entityManager = entityManager;
        this.documentoIgualValidator = documentoIgualValidator;
        this.avaliaProposta = avaliaProposta;
    }

    @PostMapping
    @Transactional
    public ResponseEntity novaProposta(@RequestBody @Valid NovaPropostaDtoRequest request,
                                       UriComponentsBuilder builder)
            throws JsonProcessingException { //3

        if(documentoIgualValidator.existe(request)) //4
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Documento inválido!");

        Proposta novaProposta = request.toProposta(); //5

        logger.info("Proposta pendente: Nome={} , Documento={}, Status Avaliação={}",
                novaProposta.getNome() , novaProposta.getDocumento(),
                novaProposta.getStatusAvaliacaoProposta());

        entityManager.persist(novaProposta);

        //servico externo
        StatusAvaliacaoProposta statusAvaliacaoProposta =
                avaliaProposta.executar(novaProposta); //6

        novaProposta.atualizaStatus(statusAvaliacaoProposta);

        logger.info("Proposta avaliada: Nome={} , Documento={}, Status Avaliação={}",
                novaProposta.getNome() , novaProposta.getDocumento(),
                novaProposta.getStatusAvaliacaoProposta());

        //atualizar proposta
        entityManager.merge(novaProposta);

        return ResponseEntity.created(builder.path("/api/propostas/{id}")
                .buildAndExpand(novaProposta.getId()).toUri()).build();
    }

}
