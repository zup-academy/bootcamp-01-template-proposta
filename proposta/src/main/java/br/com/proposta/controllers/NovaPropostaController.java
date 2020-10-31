package br.com.proposta.controllers;

import br.com.proposta.dtos.requests.PropostaRequest;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.PropostaRepository;
import br.com.proposta.services.AvaliaPropostaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/propostas")
public class NovaPropostaController {

    /* total de pontos = 6 */


    /* @complexidade - classe criada no projeto */
    private final AvaliaPropostaService avaliaPropostaService;

    /* @complexidade - classe criada no projeto */
    private final PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    private final EntityManager entityManager;


    public NovaPropostaController(AvaliaPropostaService avaliaPropostaService, PropostaRepository propostaRepository,
                                  EntityManager entityManager) {

        this.avaliaPropostaService = avaliaPropostaService;
        this.propostaRepository = propostaRepository;
        this.entityManager = entityManager;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<?> novaProposta(@RequestBody @Valid PropostaRequest propostaRequest, UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {


            /* @complexidade - classe criada no projeto */
            var novaProposta = propostaRequest.toModel();


            /* @complexidade - if */
            if(novaProposta.ehUnica(propostaRepository)){



                propostaRepository.save(novaProposta);


                /* @complexidade - função como parâmetro  */
                novaProposta.atualizaStatusElegibilidade(avaliaPropostaService.retornarAvaliacao(novaProposta));



                entityManager.merge(novaProposta);


                logger.info("Proposta documento={} salário={} criada com sucesso!", novaProposta.getIdentificacao(), novaProposta.getSalario());


                return ResponseEntity
                        .created(uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(novaProposta.getId()).toUri()).build();

            }


            /* @complexidade - exceção no fluxo do programa */
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Número de identificação já existe");


    }
}
