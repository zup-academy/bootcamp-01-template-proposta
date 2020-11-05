package br.com.zup.bootcamp.proposta.api.controller;


import br.com.zup.bootcamp.proposta.api.dto.RequestPropostaDto;
import br.com.zup.bootcamp.proposta.domain.repository.PropostaRepository;
import br.com.zup.bootcamp.proposta.domain.service.PropostaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
public class CadastraPropostaController {

    private final Logger logger = LoggerFactory.getLogger(CadastraPropostaController.class);
    //1
    private PropostaService service;
                    //1
    private PropostaRepository repository;

    public CadastraPropostaController(PropostaService service, PropostaRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping(value = "/propostas")                          //1
    public ResponseEntity<?> adiciona(@RequestBody @Valid RequestPropostaDto request, UriComponentsBuilder uriComponentsBuilder) {
        var ultimosDigitosDocumento = request.getDocumento().substring(request.getDocumento().length()-5);
        logger.info("Nova request recebida. Final do documento {} ", ultimosDigitosDocumento);
        //1
       if (repository.findByDocumento(request.getDocumento()).isPresent()){
           logger.warn("Documento com o final {} ja consta na base de dados", ultimosDigitosDocumento);
           return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
       }
       var propostaAvaliada = service.salvaProposta(request.toEntity());
        logger.info("Proposta {} e com documentocom o final {} adiciona com sucesso", propostaAvaliada.getId(),
                propostaAvaliada.getDocumento().substring(propostaAvaliada.getDocumento().length()-5));

       return ResponseEntity.created(uriComponentsBuilder.path("/proposta/{id}").buildAndExpand(propostaAvaliada.getId()).toUri()).build();
    }
}
