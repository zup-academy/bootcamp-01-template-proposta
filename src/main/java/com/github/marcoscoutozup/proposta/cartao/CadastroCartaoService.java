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
import java.util.UUID;

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
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void verificarSeExisteCartaoCadastradoNaProposta(){
        logger.info("[SCHEDULED] Verificar se existe cartão cadastrado na proposta");

                    //2
        TypedQuery<Proposta> propostas = entityManager.createNamedQuery("findPropostaByStatus", Proposta.class)
                .setParameter("statusDaProposta", StatusDaProposta.ELEGIVEL);

        //3
        for (Proposta proposta : propostas.getResultList()) {
            //4
            if(proposta.verificarSeNaoExisteCartao()){
                //5
                CartaoResponse cartaoResponse = pesquisarCartao(proposta.getId());
                Assert.notNull(cartaoResponse, "O cartão não pode ser nulo");
                //6
                Cartao cartao = cartaoResponse.toCartao();
                cadastrarCartaoEAssociarAProposta(proposta, cartao);
            }
        }
    }

    private CartaoResponse pesquisarCartao(UUID id){
        return cartaoClient.pesquisarCartaoPorIdDaProposta(id);
    }

    @Transactional
    private void cadastrarCartaoEAssociarAProposta(Proposta proposta, Cartao cartao){
        cartao.incluirPropostaNoCartao(proposta);
        entityManager.persist(cartao);
        proposta.incluirCartaoNaProposta(cartao);
        entityManager.merge(proposta);
        logger.info("[INCLUSÃO DE CARTÃO NA PROPOSTA] Cartão incluso na proposta {}", proposta.getId());
    }

}
