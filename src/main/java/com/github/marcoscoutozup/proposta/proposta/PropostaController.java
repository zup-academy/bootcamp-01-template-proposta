package com.github.marcoscoutozup.proposta.proposta;

import org.slf4j.Logger;
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

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Logger logger;

    @PostMapping
    @Transactional                                                  //1
    public ResponseEntity cadastrarProposta(@RequestBody @Valid PropostaDTO dto, UriComponentsBuilder uri){
        Proposta proposta = dto.toProposta();
        entityManager.persist(proposta);

        logger.info("Proposta criada com sucesso: " + proposta.toString());

        return ResponseEntity
                .created(uri.path("/proposta/{id}")
                .buildAndExpand(proposta.getId()).toUri())
                .build();
    }
}
