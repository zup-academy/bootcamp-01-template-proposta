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

    //1
    private AvaliaPropostaService avaliaPropostaService;

    //1
    private PropostaRepository propostaRepository;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    private EntityManager entityManager;


    public NovaPropostaController(AvaliaPropostaService avaliaPropostaService, PropostaRepository propostaRepository,
                                  EntityManager entityManager) {

        this.avaliaPropostaService = avaliaPropostaService;
        this.propostaRepository = propostaRepository;
        this.entityManager = entityManager;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<?> novaProposta(@RequestBody @Valid PropostaRequest propostaRequest, UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {


            //1
            Proposta novaProposta = propostaRequest.toModel();


            //1
            if(novaProposta.ehUnica(propostaRepository)){


                propostaRepository.save(novaProposta);


                //1
                novaProposta.atualizaStatusElegibilidade(avaliaPropostaService.retornarAvaliacao(novaProposta));


                entityManager.merge(novaProposta);


                logger.info("Proposta documento={} salário={} criada com sucesso!",
                        novaProposta.getIdentificacao(), novaProposta.getSalario());


                return ResponseEntity
                        .created(uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(novaProposta.getId()).toUri()).build();

            }


            //1
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Número de identificação já existe");


    }
}
