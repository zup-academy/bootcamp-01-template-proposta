package br.com.proposta.controllers;


import br.com.proposta.dtos.requests.PropostaRequest;
import br.com.proposta.models.Proposta;
import br.com.proposta.repositories.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;


@RestController
@RequestMapping("/propostas")
public class NovaPropostaController {


    @Autowired
    private PropostaRepository propostaRepository;


    @PostMapping
    public ResponseEntity<?> novaProposta(@RequestBody @Valid PropostaRequest propostaRequest,
                                          UriComponentsBuilder uriComponentsBuilder){

        Proposta novaProposta = propostaRequest.toModel();

        propostaRepository.save(novaProposta);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(novaProposta.getId()).toUri()).body(novaProposta);

    }
}
