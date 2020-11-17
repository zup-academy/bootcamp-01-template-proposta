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
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/cartoes")
public class BiometriaController {
// 4 PCI's

    @PersistenceContext
    private EntityManager manager;

    private final Logger logger = LoggerFactory.getLogger(Biometria.class);

    @Transactional
    @PostMapping("/{idCartao}/biometria")
    public ResponseEntity cadastroBiometria(@PathVariable Long idCartao,
                                            @RequestBody @Valid BiometriaRequest request, //PCI 1
                                            UriComponentsBuilder builder) {

        //PCI 2
        Cartao cartao = manager.find(Cartao.class, idCartao);

        //PCI 3
        if (cartao==null){
            logger.error("Cartão não encontrado para cadastramento de biometria");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //PCI 4
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