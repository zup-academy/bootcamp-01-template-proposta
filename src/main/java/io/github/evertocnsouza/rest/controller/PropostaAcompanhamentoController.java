package io.github.evertocnsouza.rest.controller;

import io.github.evertocnsouza.domain.entity.Proposta;
import io.github.evertocnsouza.rest.dto.response.PropostaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@RequestMapping("propostas")
public class PropostaAcompanhamentoController {
//3 PCI's
    @PersistenceContext
    EntityManager manager;

    private Logger logger = LoggerFactory.getLogger(PropostaAcompanhamentoController.class);

    @GetMapping("{id}")
    public ResponseEntity<?> acompanha(@PathVariable("id") Long id) {
        Proposta proposta = manager.find(Proposta.class, id);
        //PCI 1;

        //PCI 2
        if(proposta == null){
        logger.info("Esta proposta não existe");
            return ResponseEntity.notFound().build();
        }
        logger.info("Proposta  de id={} encontrada", id);

        //PCI 3;
        PropostaResponse propostaResponse = new PropostaResponse(proposta);
        return ResponseEntity.ok(propostaResponse);
    }
}
