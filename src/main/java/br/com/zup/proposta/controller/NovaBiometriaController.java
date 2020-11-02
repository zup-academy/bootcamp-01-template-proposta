package br.com.zup.proposta.controller;

import br.com.zup.proposta.dto.NovaBiometriaRequest;
import br.com.zup.proposta.model.Biometria;
import br.com.zup.proposta.model.Cartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/cartoes")
public class NovaBiometriaController {

    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(NovaBiometriaController.class);

    public NovaBiometriaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/{id}/biometrias")
    @Transactional
    public ResponseEntity novaBiometria(@PathVariable("id") UUID numeroCartao,
                                        @Valid NovaBiometriaRequest request,
                                        UriComponentsBuilder builder){ //1

        Optional<Cartao> possivelCartao = Optional
                .ofNullable(entityManager.find(Cartao.class, numeroCartao)); //2

        if (possivelCartao.isEmpty()) //3
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        Cartao novoCartao = possivelCartao.get(); //4

        logger.info("Número do cartão: {}", possivelCartao.get().getNumero());

        novoCartao.associaBiometria(request.getDigital());

        entityManager.merge(novoCartao);

        return ResponseEntity.created(builder.path("/api/cartoes/{id}/biometrias/")
        .buildAndExpand(novoCartao.getId()).toUri()).build();
    }

}
