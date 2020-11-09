package br.com.zup.bootcamp.proposta.api.controller;


import br.com.zup.bootcamp.proposta.api.dto.RequestPropostaDto;
import br.com.zup.bootcamp.proposta.domain.repository.PropostaRepository;
import br.com.zup.bootcamp.proposta.domain.service.CadastrarPropostaService;
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
    private CadastrarPropostaService service;
                    //1
    private PropostaRepository repository;

    public CadastraPropostaController(CadastrarPropostaService service, PropostaRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping(value = "/propostas")                          //1
    public ResponseEntity<?> cadastrarProposta(@RequestBody @Valid RequestPropostaDto request, UriComponentsBuilder uri) {
        var ultimosDigitosDocumento = request.getDocumento().substring(request.getDocumento().length()-5);
        logger.info("Iniciando processo de cadastro de proposta do documento com final {} ", ultimosDigitosDocumento);
        //1
       if (repository.findByDocumento(request.getDocumento()).isPresent()){
           logger.warn("Documento com o final {} ja consta na base de dados", ultimosDigitosDocumento);
           return ResponseEntity.notFound().build();
       }
       var propostaAvaliada = service.salvaProposta(request.toEntity());
        logger.info("Proposta id: {} e documento com o final {} adiciona com sucesso", propostaAvaliada.getId(),
                propostaAvaliada.getDocumento().substring(propostaAvaliada.getDocumento().length()-5));

       return ResponseEntity.created(uri.path("/propostas/{id}").buildAndExpand(propostaAvaliada.getId()).toUri()).build();
    }
}
