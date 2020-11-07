package br.com.zup.proposta.service;


import br.com.zup.proposta.dto.response.CartaoResponse;
import br.com.zup.proposta.enums.StatusAvaliacaoProposta;
import br.com.zup.proposta.integration.IntegracaoCartao;
import br.com.zup.proposta.model.Cartao;
import br.com.zup.proposta.model.Proposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
public class CadastroCartaoService {

    private Logger logger;
    private IntegracaoCartao integracaoCartao;

    private EntityManager entityManager;

    public CadastroCartaoService(IntegracaoCartao integracaoCartao, EntityManager entityManager) {
        this.integracaoCartao = integracaoCartao;
        this.entityManager = entityManager;
        this.logger = LoggerFactory.getLogger(CadastroCartaoService.class);

    }

    @Scheduled(fixedDelayString = "${tempo.verificadordecartao}")
    @Transactional
    public void verificarSeExisteCartaoCadastradoNaProposta() {
        logger.info("[SCHEDULED] Verificar se existe cartão cadastrado na proposta ["+ LocalDateTime.now() +"]");

        TypedQuery<Proposta> propostas = entityManager.createNamedQuery("findPropostaByStatus", Proposta.class)
                .setParameter("statusAvaliacao", StatusAvaliacaoProposta.ELEGIVEL);

        propostas.getResultList().forEach(proposta -> {
            CartaoResponse cartaoResponse = integracaoCartao.pesquisarCartaoPorIdDaProposta(proposta.getId().toString());
            if (proposta.verificarSeNaoExisteCartao()) {
                Assert.notNull(cartaoResponse, "O cartão não pode ser nulo");
                Cartao cartao = cartaoResponse.toModel();
                entityManager.persist(cartao);
                proposta.incluirCartaoNaProposta(cartao);
                entityManager.merge(proposta);
                logger.info("[INCLUSÃO DE CARTÃO NA PROPOSTA] Cartão incluso na proposta {}", proposta.getId());
            }
        });
    }
}


