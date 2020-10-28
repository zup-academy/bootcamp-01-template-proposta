package br.com.proposta.controllers;


import br.com.proposta.dtos.requests.PropostaRequest;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.PropostaRepository;
import br.com.proposta.services.AvaliaPropostaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/propostas")
public class NovaPropostaController {


    /* pontos de complexidade = 6 */

    //1
    @Autowired
    private AvaliaPropostaService avaliaPropostaService;

    //1
    @Autowired
    private PropostaRepository propostaRepository;


    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    @PostMapping
    public ResponseEntity<?> novaProposta(@RequestBody @Valid PropostaRequest propostaRequest, UriComponentsBuilder uriComponentsBuilder){


            //1
            Proposta novaProposta = propostaRequest.toModel();


            //1
            if(novaProposta.ehUnica(propostaRepository)){


                propostaRepository.save(novaProposta);


                //1
                novaProposta.atualizaStatusElegibilidade(avaliaPropostaService.retornarAvaliacao(novaProposta));


                propostaRepository.save(novaProposta);


                logger.info("Proposta documento={} salário={} criada com sucesso!",
                        novaProposta.getIdentificacao(), novaProposta.getSalario());


                return ResponseEntity
                        .created(uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(novaProposta.getId()).toUri()).build();

            }


            //1
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Número de identificação já existe");


    }
}
