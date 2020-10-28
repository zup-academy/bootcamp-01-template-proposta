package br.com.zup.proposta.controller;

import br.com.zup.proposta.dto.NovaPropostaDtoRequest;
import br.com.zup.proposta.model.Proposta;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    @PersistenceContext
    EntityManager entityManager;

    @PostMapping
    @Transactional
    public ResponseEntity novaProposta(@RequestBody @Valid NovaPropostaDtoRequest request,
                                       UriComponentsBuilder builder){

        Proposta novaProposta = request.toProposta();

        entityManager.persist(novaProposta);

        return ResponseEntity.created(builder.path("/api/propostas/{id}")
                .buildAndExpand(novaProposta.getId())
                .toUri())
                .build();
    }

}
