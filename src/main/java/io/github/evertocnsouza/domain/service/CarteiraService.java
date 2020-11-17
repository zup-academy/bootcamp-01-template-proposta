package io.github.evertocnsouza.domain.service;

import io.github.evertocnsouza.domain.entity.Cartao;
import io.github.evertocnsouza.domain.entity.Carteira;
import io.github.evertocnsouza.domain.enums.TipoCarteira;
import io.github.evertocnsouza.domain.repository.IntegracaoCartao;
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

    private final IntegracaoCartao integracaoCartao;
    private final EntityManager manager;
    private Logger logger = LoggerFactory.getLogger(CarteiraService.class);

    public CarteiraService(IntegracaoCartao integracaoCartao, EntityManager manager) {
        this.integracaoCartao = integracaoCartao;
        this.manager = manager;
    }

    public ResponseEntity AssociarCarteiraComCartao(TipoCarteira tipoCarteira, Cartao cartao, String email, UriComponentsBuilder builder){
        Map carteiraRequest = new HashMap();
        carteiraRequest.put("email", email);
        carteiraRequest.put("carteira", tipoCarteira);
        logger.info("Enviando carteira associada para o legado de cartões");
        ResponseEntity responseEntity = integracaoCartao.associarCarteira(carteiraRequest, cartao.getNumeroCartao());

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            logger.info("Salvando carteira e associando com cartão {}", cartao.getId());

            Carteira carteira = new Carteira(email, tipoCarteira);
            manager.persist(carteira);

            cartao.addCarteira(carteira);
            manager.merge(cartao);

            return ResponseEntity
                    .created(builder.path("/carteiras/{id}").buildAndExpand(carteira.getId())
                            .toUri()).build();
        }
        logger.info("{} erro no retorno do legado de cartões", responseEntity.getStatusCode());
        return ResponseEntity.badRequest().build();
    }
}