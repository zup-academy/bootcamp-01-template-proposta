package com.github.marcoscoutozup.proposta.cartao;

import com.github.marcoscoutozup.proposta.proposta.Proposta;
import com.github.marcoscoutozup.proposta.proposta.enums.StatusDaProposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Component
public class CadastroCartaoService {

            //1
    private CartaoClient cartaoClient;
    private EntityManager entityManager;
    private Logger logger;

    public CadastroCartaoService(CartaoClient cartaoClient, EntityManager entityManager) {
        this.cartaoClient = cartaoClient;
        this.entityManager = entityManager;
        this.logger = LoggerFactory.getLogger(CadastroCartaoService.class);
    }

    @Scheduled(fixedDelayString = "${tempo.verificadordecartao}")
    @Transactional
    public void verificarSeExisteCartaoCadastradoNaProposta(){
        logger.info("[SCHEDULED] Verificar se existe cartão cadastrado na proposta");

                //2
        TypedQuery<Proposta> propostas = entityManager.createNamedQuery("findPropostaByStatus", Proposta.class)
                .setParameter("statusDaProposta", StatusDaProposta.ELEGIVEL);

                            //3
        propostas.getResultList().forEach(proposta -> {
            //4
            CartaoResponse cartaoResponse = cartaoClient.pesquisarCartaoPorIdDaProposta(proposta.getId().toString());

            //5
            if(proposta.verificarSeNaoExisteCartao()){
                Assert.notNull(cartaoResponse, "O cartão não pode ser nulo");
                Cartao cartao = cartaoResponse.toCartao();
                entityManager.persist(cartao);
                proposta.incluirCartaoNaProposta(cartao);
                entityManager.merge(proposta);
                logger.info("[INCLUSÃO DE CARTÃO NA PROPOSTA] Cartão incluso na proposta {}", proposta.getId());
            }
        });
    }

}
