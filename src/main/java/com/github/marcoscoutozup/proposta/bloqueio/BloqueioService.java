package com.github.marcoscoutozup.proposta.bloqueio;

import com.github.marcoscoutozup.proposta.cartao.Cartao;
import com.github.marcoscoutozup.proposta.cartao.CartaoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BloqueioService {

            //1
    private CartaoClient cartaoClient;
    private Logger logger = LoggerFactory.getLogger(BloqueioService.class);

    @Value("${spring.application.name}")
    private String nomeDoSistema;

    public BloqueioService(CartaoClient cartaoClient) {
        this.cartaoClient = cartaoClient;
    }

    public void processarBloqueioDoCartao(Cartao cartao){
        //2
        if(cartao.verificarSeOCartaoEstaBloqueado()){
            logger.info("[BLOQUEIO DE CARTÃO] O cartão já está bloqueado no sistema. Cartão: {}", cartao.getId());
            return;
        }

        Map bloqueioRequest = new HashMap();
        bloqueioRequest.put("sistemaResponsavel", nomeDoSistema);
        ResponseEntity responseEntity = cartaoClient.bloquearCartao(cartao.getId(), bloqueioRequest);

        //3
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            cartao.bloquearCartao();
            logger.info("[BLOQUEIO DE CARTÃO] Cartão bloqueado: {}", cartao.getId());
        }
    }
}
