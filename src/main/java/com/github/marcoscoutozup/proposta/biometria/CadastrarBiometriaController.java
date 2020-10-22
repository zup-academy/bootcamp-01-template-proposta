package com.github.marcoscoutozup.proposta.biometria;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.cartao.CartaoRepository;
import com.github.marcoscoutozup.proposta.exception.StandardError;
import org.apache.tomcat.util.codec.binary.Base64;
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
@RequestMapping("/biometrias")
public class CadastrarBiometriaController {

    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(Biometria.class);

    public CadastrarBiometriaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/cadastrar/{idCartao}")
    @Transactional                                                                              //2
    public ResponseEntity cadastrarBiometria(@PathVariable UUID idCartao, @RequestBody @Valid BiometriaDTO dto, UriComponentsBuilder uri){

        Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));

        //3
        if(cartaoProcurado.isEmpty()){
            logger.warn("[CADASTRO DE BIOMETRIA] O número do cartão não foi encontrado. Id: {}", idCartao);
                                                                            //4
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(Arrays.asList("Cartão não encontrado")));
        }

            //5
        Biometria biometria = dto.toBiometria();
        entityManager.persist(biometria);
        logger.warn("[CADASTRO DE BIOMETRIA] Biometria cadastrada: {}", biometria.toString());

        //6
        Cartao cartao = cartaoProcurado.get();
        cartao.incluirBiometriaNoCartao(biometria);
        entityManager.merge(cartao);
        logger.warn("[CADASTRO DE BIOMETRIA] Biometria associada ao cartão: {}", cartao.toString());

        return ResponseEntity
                .created(uri.path("/biometrias/{id}")
                        .buildAndExpand(biometria.getId())
                        .toUri())
                .build();
    }

}
