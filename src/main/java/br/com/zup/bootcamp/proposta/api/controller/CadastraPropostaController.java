package br.com.zup.bootcamp.proposta.api.controller;


import br.com.zup.bootcamp.proposta.api.dto.RequestPropostaDto;
import br.com.zup.bootcamp.proposta.api.handler.VerificaDocumentoCpfCnpjValidator;
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

    @PersistenceContext
    private EntityManager manager;
    @Autowired
    private VerificaDocumentoCpfCnpjValidator documentoValidator;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(documentoValidator);
    }

    @PostMapping(value = "/propostas")
    @Transactional
    public ResponseEntity<?> adiciona(@RequestBody @Valid RequestPropostaDto request, UriComponentsBuilder uriComponentsBuilder) {
        var proposta = request.toEntity();
        manager.persist(proposta);
        return ResponseEntity.created(uriComponentsBuilder.path("/proposta/{id}").buildAndExpand(proposta.getId()).toUri()).build();
    }
}
