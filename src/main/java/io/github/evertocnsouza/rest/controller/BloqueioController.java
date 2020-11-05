package io.github.evertocnsouza.rest.controller;

import io.github.evertocnsouza.domain.entity.Bloqueio;
import io.github.evertocnsouza.domain.entity.Cartao;
import io.github.evertocnsouza.domain.service.BloqueioService;
import io.github.evertocnsouza.validation.annotation.InfoRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RequestMapping("/cartoes")
@RestController
public class BloqueioController {
    //4 PCI's
    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private BloqueioService bloqueioService;
    //PCI 1

    private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);

    @PostMapping("/{idCartao}/bloquear")
    @Transactional
    public ResponseEntity bloquearCartao(@PathVariable Long idCartao,
                                         @InfoRequest HttpServletRequest request,
                                         UriComponentsBuilder builder) {

        //PCI 2
        Cartao cartao = manager.find(Cartao.class, idCartao);
        logger.info("Cartao encontrado para ser bloqueado");

        //PCI 3
        if (cartao==null){
            logger.info("Cartão não encontrado para ser bloqueado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //PCI 4
        Bloqueio bloqueio = new Bloqueio(request.getRemoteAddr(), request.getHeader("User-Agent"));
        manager.persist(bloqueio);

        bloqueioService.bloqueandoCartao(cartao);
        cartao.addBloqueioDoCartao(bloqueio);
        manager.merge(cartao);

        logger.info("Bloqueio cartão={} cadastrado", cartao.getNumeroCartao());

        return ResponseEntity.created(builder.path("/bloqueios/{id}").buildAndExpand(bloqueio.getId())
                .toUri()).build();
    }
}
