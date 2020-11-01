package io.github.evertocnsouza.rest.controller;

import io.github.evertocnsouza.domain.entity.Biometria;
import io.github.evertocnsouza.domain.entity.Cartao;
import io.github.evertocnsouza.rest.dto.request.BiometriaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class BiometriaController {

    private EntityManager manager;

    private Logger logger = LoggerFactory.getLogger(Biometria.class);

    public BiometriaController(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional
    @PostMapping("/{idCartao}/biometria")
    public ResponseEntity cadastroBiometria(@PathVariable Long idCartao,
                                            @RequestBody @Valid BiometriaRequest request,
                                            UriComponentsBuilder builder) {

        Cartao cartao = manager.find(Cartao.class, idCartao);

        if (cartao==null){
            logger.info("Cartão não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Biometria biometria = request.toBiometria();
        manager.persist(biometria);
        logger.info("Biometria cadastrada");


        cartao.addBiometria(biometria);
        manager.merge(cartao);
        logger.info("Biometria associada ao cartao={}", cartao.getNumeroCartao());

        return ResponseEntity.created(builder.path("/biometrias/{id}").buildAndExpand(idCartao)
                .toUri()).build();
    }

}