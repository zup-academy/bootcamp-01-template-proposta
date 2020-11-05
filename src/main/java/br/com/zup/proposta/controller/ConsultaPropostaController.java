package br.com.zup.proposta.controller;

import br.com.zup.proposta.integration.IntegracaoCartao;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.utils.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping(value = "propostas")
public class ConsultaPropostaController {

    private IntegracaoCartao cartaoClient;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/{id}")
    public ResponseEntity buscaPropostaId(@PathVariable UUID id) {

        Proposta proposta = entityManager.find(Proposta.class, id);

        if(proposta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(Arrays.asList("Não existe proposta cadastrada")));
        }

        return ResponseEntity.ok(proposta);
    }
}
