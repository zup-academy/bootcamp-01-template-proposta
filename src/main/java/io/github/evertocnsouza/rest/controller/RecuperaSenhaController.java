package io.github.evertocnsouza.rest.controller;

import io.github.evertocnsouza.domain.entity.Cartao;
import io.github.evertocnsouza.domain.entity.RecuperarSenha;
import io.github.evertocnsouza.validation.annotation.InfoRequest;
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
import java.util.Optional;

@RestController
@RequestMapping("/cartoes")
@Validated
public class RecuperaSenhaController {
    //3 PCI's;

    @PersistenceContext
    private EntityManager manager;

    private Logger logger = LoggerFactory.getLogger(RecuperaSenhaController.class);

    @PostMapping("/{idCartao}/recuperarsenha")
    @Transactional
    public ResponseEntity RecuperacaoDeSenha(@PathVariable Long idCartao,
                                             @InfoRequest HttpServletRequest request,
                                             UriComponentsBuilder builder){

        Optional<Cartao> cartao = Optional.ofNullable(manager.find(Cartao.class, idCartao));
        //PCI 1 e 2
        if (cartao==null) {
            logger.error("Cartão não encontrado para troca de senha");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //PCI 3;
        RecuperarSenha recuperarSenha = new RecuperarSenha(request.getRemoteAddr(), request.getHeader("User-Agent"), cartao.get());
        manager.persist(recuperarSenha);
        logger.warn("Solicitando recuperação de senha cadastrada. Id: {}", recuperarSenha.getId());

        return ResponseEntity.created(builder.path("/recuperarsenha/{id}").buildAndExpand(recuperarSenha.getId()).toUri()).build();
    }
}