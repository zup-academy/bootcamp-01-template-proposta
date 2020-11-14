package com.github.marcoscoutozup.proposta.biometria;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class CadastrarBiometriaController {

    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(Biometria.class);

    public CadastrarBiometriaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/{idCartao}/biometria")
    @Transactional                                                                              //1
    public ResponseEntity cadastrarBiometria(@PathVariable UUID idCartao,
                                             @RequestBody @Valid BiometriaRequest biometriaRequest,
                                             @RequestHeader(name = "Authorization") String token,
                                             UriComponentsBuilder uri){

                //2
        Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));

        //3
        if(cartaoProcurado.isEmpty()){
            logger.warn("[CADASTRO DE BIOMETRIA] O número do cartão não foi encontrado. Id: {}", idCartao);
                                                                            //4
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(Arrays.asList("Cartão não encontrado")));
        }

        Cartao cartao = cartaoProcurado.get();

        //5
        if(!cartao.verificarSeOEmailDoTokenEOMesmoDoCartao(token)){
            logger.warn("[CADASTRO DE AVISO] O email não token não corresponde ao proprietário do cartão. Id: {}", idCartao);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new StandardError(Arrays.asList("Cartão não pertencente ao solicitante")));
        }

            //6
        Biometria biometria = biometriaRequest.toBiometria();
        entityManager.persist(biometria);
        logger.warn("[CADASTRO DE BIOMETRIA] Biometria cadastrada: {}", biometria.getId());

        cartao.incluirBiometriaNoCartao(biometria);
        entityManager.merge(cartao);
        logger.warn("[CADASTRO DE BIOMETRIA] Biometria associada ao cartão: {}", cartao.getNumeroCartao());

        return ResponseEntity
                .created(uri.path("/biometrias/{id}")
                        .buildAndExpand(idCartao)
                        .toUri())
                .build();
    }

}
