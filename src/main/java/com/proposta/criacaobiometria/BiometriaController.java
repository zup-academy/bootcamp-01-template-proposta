package com.proposta.criacaobiometria;


import com.proposta.criacaocartao.Cartao;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/biometrias")
@Validated
public class BiometriaController {

    @PersistenceContext
    EntityManager manager;

    @PostMapping("/{idCartao}")
    @Transactional
    public ResponseEntity<?> cria(@PathVariable String idCartao, @RequestBody @Valid BiometriaRequest request, UriComponentsBuilder builder){

        Cartao cartao = manager.find(Cartao.class, idCartao);
        if (cartao == null) {
            return ResponseEntity.badRequest().body("Cartão inválido.");
        }

        Biometria biometria = request.toModel(cartao);
        manager.persist(biometria);

        URI uriCreated = builder.path("/biometrias/{id}").build(biometria.getId());

        return ResponseEntity.created(uriCreated).build();
    }
}
