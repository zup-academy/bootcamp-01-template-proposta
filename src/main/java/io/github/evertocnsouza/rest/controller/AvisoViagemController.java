package io.github.evertocnsouza.rest.controller;

import io.github.evertocnsouza.domain.entity.AvisoViagem;
import io.github.evertocnsouza.domain.entity.Biometria;
import io.github.evertocnsouza.domain.entity.Cartao;
import io.github.evertocnsouza.domain.repository.IntegracaoCartao;
import io.github.evertocnsouza.rest.dto.request.AvisoViagemRequest;
import io.github.evertocnsouza.validation.annotation.InfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/cartoes")
@Validated
public class AvisoViagemController {
    //5 PCI's

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private IntegracaoCartao integracaoCartao;
    //PCI 1

    private final Logger logger = LoggerFactory.getLogger(Biometria.class);

    @PostMapping("/{idCartao}/aviso")
    @Transactional
    public ResponseEntity cadastroAviso(@PathVariable Long idCartao,
                                        @RequestBody @Valid AvisoViagemRequest avisoRequest, //PCI 2
                                        @InfoRequest HttpServletRequest request,
                                        UriComponentsBuilder builder) {

        Cartao cartao = manager.find(Cartao.class, idCartao);
        //PCI 3 e PCI 4
        if (cartao==null){
            logger.error("Cartão não encontrado para aviso de viagem");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //PCI 5
        AvisoViagem avisoViagem = avisoRequest.toAviso(request);
        manager.persist(avisoViagem);
        logger.info("Aviso cadastrado! Aviso número={}", avisoViagem.getId());

        cartao.addAvisoViagem(avisoViagem);
        manager.merge(cartao);
        logger.info("Aviso associado ao cartão={}", cartao.getNumeroCartao());

        return ResponseEntity.created(builder.path("/avisos/{id}").buildAndExpand(avisoViagem.getId())
                .toUri()).build();
    }
}
