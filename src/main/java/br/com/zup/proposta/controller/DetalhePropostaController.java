package br.com.zup.proposta.controller;

import br.com.zup.proposta.dto.DetalhePropostaResponse;
import br.com.zup.proposta.model.Proposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/propostas")
public class DetalhePropostaController {

    private EntityManager entityManager;

    public DetalhePropostaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("/{id}")
    public ResponseEntity buscaPropostabyId(@PathVariable("id") UUID request){

        //ver propostas de outros usuários?

        Optional<Proposta> possivelProposta =
                Optional.ofNullable(entityManager.find(Proposta.class, request)); //1

        if (possivelProposta.isEmpty()) //2
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        DetalhePropostaResponse response =
                new DetalhePropostaResponse(possivelProposta.get()); //3

        return new ResponseEntity(response, HttpStatus.OK);
    }

}
