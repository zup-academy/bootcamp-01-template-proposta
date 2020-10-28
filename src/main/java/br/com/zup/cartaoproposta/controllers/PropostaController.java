package br.com.zup.cartaoproposta.controllers;

import br.com.zup.cartaoproposta.entities.proposta.Proposta;
import br.com.zup.cartaoproposta.entities.proposta.PropostaNovoRequest;
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

/**
 * Contagem de carga intrínseca da classe: 2
 */

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping
    @Transactional
    //1
    public ResponseEntity<String> criaProposta(@RequestBody @Valid PropostaNovoRequest novaProposta, UriComponentsBuilder uriComponentsBuilder) {

        //1
        Proposta proposta = novaProposta.toModel();
        manager.persist(proposta);

        URI link = uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(link).build();
    }
}
