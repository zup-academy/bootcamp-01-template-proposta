package br.com.zup.bootcamp.proposta.api.controller;


import br.com.zup.bootcamp.proposta.api.dto.RequestPropostaDto;
import br.com.zup.bootcamp.proposta.api.handler.VerificaDocumentoCpfCnpjValidator;
import br.com.zup.bootcamp.proposta.domain.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class CadastraPropostaController {

    private VerificaDocumentoCpfCnpjValidator documentoValidator;
    private PropostaRepository repository;

    public CadastraPropostaController(VerificaDocumentoCpfCnpjValidator documentoValidator, PropostaRepository repository) {
        this.documentoValidator = documentoValidator;
        this.repository = repository;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(documentoValidator);
    }

    @PostMapping(value = "/propostas")                          //1
    public ResponseEntity<?> adiciona(@RequestBody @Valid RequestPropostaDto request, UriComponentsBuilder uriComponentsBuilder) {
        //1
       if (repository.findByDocumento(request.getDocumento()).isPresent()){
           return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
       }
            //1
       var proposta = request.toEntity();
       repository.save(proposta);
       return ResponseEntity.created(uriComponentsBuilder.path("/proposta/{id}").buildAndExpand(proposta.getId()).toUri()).build();
    }
}
