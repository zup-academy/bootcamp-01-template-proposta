package com.proposta.criacaoproposta;

import com.proposta.metrics.MinhasMetricas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private MinhasMetricas minhasMetricas;

    @PostMapping
    @Transactional
    //1 PropostaRequest
    public ResponseEntity<?> cria(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder builder) {
        //2 Proposta
        Proposta proposta = request.toModel(manager);

        //3 PropostaResponse // 4 Proposta service
        PropostaResponse propostaResponse = propostaService.cria(proposta);

        minhasMetricas.meuContador();

        URI uriCreated = builder.path("/propostas/{id}").build(propostaResponse.getId());
        return ResponseEntity.created(uriCreated).build();
    }
}