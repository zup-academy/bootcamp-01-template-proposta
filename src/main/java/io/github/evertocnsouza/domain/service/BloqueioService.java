package io.github.evertocnsouza.domain.service;

import io.github.evertocnsouza.domain.entity.Cartao;
import io.github.evertocnsouza.domain.repository.IntegracaoCartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class BloqueioService {
    //3 PCI's

    @Autowired
    private IntegracaoCartao integracaoCartao;
    //PCI 1

    private Logger logger = LoggerFactory.getLogger(BloqueioService.class);

    @Value("${spring.application.name}")
    private String nomeDoSistema;

    public void bloqueandoCartao(Cartao cartao){
        //PCI 2
        if(cartao.verificarCartaoBloqueado()){
            logger.info("Este cartão já esta bloqueado! Cartão = {}", cartao.getId());
            return;
        }
        Map bloqueioRequest = new HashMap<>();
        bloqueioRequest.put("sistemaResponsavel", nomeDoSistema);
        ResponseEntity responseEntity = integracaoCartao.bloquearCartao(cartao.getNumeroCartao(), (HashMap) bloqueioRequest);

        //PCI 3
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            cartao.bloquearCartao();
            logger.info("Cartão bloqueado: {} ", cartao.getId());
        }
    }
}
/*Cheguei a fazer um try/catch, pois alguns cartões surgem já bloqueados.
Porém notei, que se reinicio o sistema, este erro não acontece.
Confirmando com os responsáveis, fui informado que este é um erro de memoria do Docker,
por isso, optei por manter da forma que havia feito primeiro.
 */
