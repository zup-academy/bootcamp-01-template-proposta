package com.github.marcoscoutozup.proposta.proposta;

import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    private EntityManager entityManager;
    private Logger logger;

    public PropostaController(EntityManager entityManager, Logger logger) {
        this.entityManager = entityManager;
        this.logger = logger;
    }

    @PostMapping
    @Transactional                                                  //1
    public ResponseEntity cadastrarProposta(@RequestBody @Valid PropostaDTO dto, UriComponentsBuilder uri){

        TypedQuery response = entityManager.createNamedQuery("findPropostaByDocumento", Proposta.class);
        response.setParameter("documento", dto.getDocumento());

        //2
        if(!response.getResultList().isEmpty()){                    //3
            return ResponseEntity.unprocessableEntity().body(new StandardError(Arrays.asList("Já existe uma proposta com este documento")));
        }

            //4
        Proposta proposta = dto.toProposta();
        entityManager.persist(proposta);

        logger.info("Proposta criada com sucesso: " + proposta.toString());

        return ResponseEntity
                .created(uri.path("/proposta/{id}")
                .buildAndExpand(proposta.getId()).toUri())
                .build();
    }
}
