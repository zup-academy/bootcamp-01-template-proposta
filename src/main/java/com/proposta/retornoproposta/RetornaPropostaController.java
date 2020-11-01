package com.proposta.retornoproposta;

import com.proposta.criacaoproposta.Proposta;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/propostas/{idProposta}")
public class RetornaPropostaController {

    @PersistenceContext
    EntityManager manager;

    @GetMapping
    @Transactional
    public PropostaResponse statusProposta(@PathVariable Long idProposta) {
        Proposta proposta = manager.find(Proposta.class, idProposta);

        return new PropostaResponse(proposta);
    }

}
