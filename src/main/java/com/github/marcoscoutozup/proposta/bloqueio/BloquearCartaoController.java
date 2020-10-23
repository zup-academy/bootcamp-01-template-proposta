package com.github.marcoscoutozup.proposta.bloqueio;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import com.github.marcoscoutozup.proposta.validator.requestbloqueiocartao.RequestBloqueioCartao;
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
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
@Validated
public class BloquearCartaoController {

    @PersistenceContext
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(BloquearCartaoController.class);

    @PostMapping("/{idCartao}/bloquear")
    @Transactional
    public ResponseEntity bloquearCartao(@PathVariable UUID idCartao, @RequestBloqueioCartao HttpServletRequest request, UriComponentsBuilder uri){

                    //1
        Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));

        //2
        if(cartaoProcurado.isEmpty()){
            logger.info("[BLOQUEIO DE CARTÃO] Cartão não encontrado. Id: {}", idCartao);
                                                                            //3
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(Arrays.asList("O Cartão não foi encontrado")));
        }

        //4
        Bloqueio bloqueio = new Bloqueio(request.getRemoteAddr(), request.getHeader("User-Agent"));
        entityManager.persist(bloqueio);
        logger.info("[BLOQUEIO DE CARTÃO] Cartão não encontrado. Bloqueio: {}", bloqueio.toString());

        Cartao cartao = cartaoProcurado.get();
        cartao.incluirBloqueioDoCartao(bloqueio);
        entityManager.merge(cartao);

        return ResponseEntity
                .created(uri.path("/cartoes/{id}/bloqueios")
                        .buildAndExpand(idCartao)
                        .toUri())
                .build();
    }

}
