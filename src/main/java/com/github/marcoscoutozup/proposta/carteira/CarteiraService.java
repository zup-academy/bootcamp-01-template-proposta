package com.github.marcoscoutozup.proposta.carteira;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.cartao.CartaoClient;
import com.github.marcoscoutozup.proposta.carteira.enums.TipoCarteira;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Service
public class CarteiraService {

    //1
    private CartaoClient cartaoClient;
    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(CarteiraService.class);

    public CarteiraService(CartaoClient cartaoClient, EntityManager entityManager) {
        this.cartaoClient = cartaoClient;
        this.entityManager = entityManager;
    }
                                                                     //2                       //3
    public ResponseEntity processarAssociacaoDaCarteiraComCartao(TipoCarteira tipoCarteira, Cartao cartao, String email, UriComponentsBuilder uri){
        Map carteiraRequest = new HashMap();
        carteiraRequest.put("email", email);
        carteiraRequest.put("carteira", tipoCarteira);
        logger.info("[ASSOCIAÇÃO DE CARTEIRA] Enviando a carteira para o sistema de cartões");
        ResponseEntity responseEntity = cartaoClient.associarCarteira(carteiraRequest, cartao.getNumeroCartao());

        //4
        if(responseEntity.getStatusCode() == HttpStatus.OK){
            logger.info("[ASSOCIAÇÃO DE CARTEIRA] Salvando carteira e associando com cartão {}", cartao.getId());
            //5
            Carteira carteira = new Carteira(email, tipoCarteira);
            entityManager.persist(carteira);

            cartao.incluirCarteira(carteira);
            entityManager.merge(cartao);
            return ResponseEntity
                    .created(uri.path("/carteiras/{id}")
                            .buildAndExpand(carteira.getId())
                            .toUri())
                    .build();
        }

        logger.info("[ASSOCIAÇÃO DE CARTEIRA] Erro {} no retorno do sistema de cartões", responseEntity.getStatusCode());
        return ResponseEntity.badRequest().build();
    }
}
