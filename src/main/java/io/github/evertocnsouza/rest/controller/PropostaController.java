package io.github.evertocnsouza.rest.controller;

import io.github.evertocnsouza.domain.entity.Proposta;
import io.github.evertocnsouza.rest.dto.PropostaRequest;
import org.junit.Test;
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
import java.net.URI;

@RestController
@RequestMapping("propostas")
public class PropostaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@RequestBody @Valid PropostaRequest request,
                                  UriComponentsBuilder uriComponentsBuilder) {

        Proposta proposta = request.ToModel();
        manager.persist(proposta);

        URI enderecoConsulta = uriComponentsBuilder.path("{id}").build(proposta.getId());
        return ResponseEntity.created(enderecoConsulta).build();

    }
}
