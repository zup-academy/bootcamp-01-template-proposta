package com.github.marcoscoutozup.proposta.bloqueio;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import com.github.marcoscoutozup.proposta.validator.requestbloqueiocartao.InformacoesObrigatoriasRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
@Validated
public class BloquearCartaoController {

    private EntityManager entityManager;
            //1
    private BloqueioService bloqueioService;

    public BloquearCartaoController(EntityManager entityManager, BloqueioService bloqueioService) {
        this.entityManager = entityManager;
        this.bloqueioService = bloqueioService;
    }

    private Logger logger = LoggerFactory.getLogger(BloquearCartaoController.class);

    @PostMapping("/{idCartao}/bloquear")
    @Transactional
    public ResponseEntity bloquearCartao(@PathVariable UUID idCartao, @InformacoesObrigatoriasRequest HttpServletRequest request, UriComponentsBuilder uri){

                    //2
        Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));

        //3
        if(cartaoProcurado.isEmpty()){
            logger.info("[BLOQUEIO DE CARTÃO] Cartão não encontrado. Id: {}", idCartao);
                                                                            //4
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(Arrays.asList("O Cartão não foi encontrado")));
        }

        //5
        Bloqueio bloqueio = new Bloqueio(request.getRemoteAddr(), request.getHeader("User-Agent"));
        entityManager.persist(bloqueio);

        Cartao cartao = cartaoProcurado.get();

        bloqueioService.processarBloqueioDoCartao(cartao);

        cartao.incluirBloqueioDoCartao(bloqueio);
        entityManager.merge(cartao);

        logger.info("[BLOQUEIO DE CARTÃO] Bloqueio cadastrado. Cartão: {}", cartao.toString());

        return ResponseEntity
                .created(uri.path("/bloqueios/{id}")
                        .buildAndExpand(bloqueio.getId())
                        .toUri())
                .build();
    }

}
