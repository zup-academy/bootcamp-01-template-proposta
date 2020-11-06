package com.github.marcoscoutozup.proposta.avisos;

import com.github.marcoscoutozup.proposta.biometria.Biometria;
import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.cartao.CartaoClient;
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
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
@Validated
public class CadastrarAvisoController {

    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(Biometria.class);
                //1
    private CartaoClient cartaoClient;

    public CadastrarAvisoController(EntityManager entityManager, CartaoClient cartaoClient) {
        this.entityManager = entityManager;
        this.cartaoClient = cartaoClient;
    }

    @PostMapping("/{idCartao}/aviso")
    @Transactional
    public ResponseEntity cadastrarAvisoDeViagem(@PathVariable UUID idCartao,
                                             @RequestBody @Valid AvisoRequest avisoRequest,   //2
                                             @InformacoesObrigatoriasRequest HttpServletRequest request,
                                             @RequestHeader(name = "Authorization") String token,
                                             UriComponentsBuilder uri){

                    //3
        Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));

        //4
        if(cartaoProcurado.isEmpty()){
            logger.warn("[CADASTRO DE AVISO] O número do cartão não foi encontrado. Id: {}", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    //5
                    .body(new StandardError(Arrays.asList("Cartão não encontrado")));
        }

        Cartao cartao = cartaoProcurado.get();

        //6
        if(!cartao.verificarSeOEmailDoTokenEOMesmoDoCartao(token)){
            logger.warn("[CADASTRO DE AVISO] O email não token não corresponde ao proprietário do cartão. Id: {}", idCartao);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new StandardError(Arrays.asList("Cartão não pertencente ao solicitante")));
        }

       logger.info("[CADASTRO DE AVISO] Enviando aviso de viagem para o sistema de cartões. Cartão: {}", idCartao);
       cartaoClient.enviarAvisoDeViagem(cartao.getNumeroCartao(), avisoRequest);

       //7
       Aviso aviso = avisoRequest.toAviso(request);
       entityManager.persist(aviso);
       logger.info("[CADASTRO DE AVISO] Aviso cadastrado: {}", aviso.getId());

       cartao.incluirAvisoDeViagem(aviso);
       entityManager.merge(cartao);
       logger.info("[CADASTRO DE AVISO] Aviso associado ao cartão: {}", cartao.getNumeroCartao());

       return ResponseEntity
               .created(uri.path("/avisos/{id}")
                        .buildAndExpand(aviso.getId())
                        .toUri()).build();
    }
}
