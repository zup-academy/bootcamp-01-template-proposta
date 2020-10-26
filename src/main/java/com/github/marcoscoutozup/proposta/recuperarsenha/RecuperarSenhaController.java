package com.github.marcoscoutozup.proposta.recuperarsenha;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import com.github.marcoscoutozup.proposta.validator.requestbloqueiocartao.InformacoesObrigatoriasRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
public class RecuperarSenhaController {

    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(RecuperarSenhaController.class);

    public RecuperarSenhaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/{idCartao}/recuperarsenha")
    @Transactional
    public ResponseEntity solicitarRecuperacaoDeSenha(@PathVariable UUID idCartao, @InformacoesObrigatoriasRequest HttpServletRequest request, UriComponentsBuilder uri){

                    //1
        Optional<Cartao> cartao = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));

        //2
        if(cartao.isEmpty()){
            logger.warn("[RECUPERAÇÃO DE SENHA] O número do cartão não foi encontrado. Id: {}", idCartao);
                                                                            //3
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(Arrays.asList("Cartão não encontrado")));
        }

        //4
        RecuperarSenha recuperarSenha = new RecuperarSenha(request.getRemoteAddr(), request.getHeader("User-Agent"), cartao.get());
        entityManager.persist(recuperarSenha);

        logger.warn("[RECUPERAÇÃO DE SENHA] Solicitação de recuperação de senha cadastrada. Id: {}", recuperarSenha.getId());

        return ResponseEntity
                .created(uri.path("/recuperarsenha/{id}")
                        .buildAndExpand(recuperarSenha.getId())
                        .toUri())
                .build();
    }
}
