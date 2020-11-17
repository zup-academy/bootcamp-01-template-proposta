package io.github.evertocnsouza.domain.service;

import io.github.evertocnsouza.domain.entity.Cartao;
import io.github.evertocnsouza.domain.entity.Proposta;
import io.github.evertocnsouza.domain.enums.StatusAvaliacaoProposta;
import io.github.evertocnsouza.rest.dto.response.CartaoResponse;
import io.github.evertocnsouza.domain.repository.IntegracaoCartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Service
public class CartaoService {

    private final IntegracaoCartao integracaoCartao;
    private final EntityManager manager;
    private final Logger logger;

    public CartaoService(IntegracaoCartao integracaoCartao, EntityManager manager) {
        this.integracaoCartao = integracaoCartao;
        this.manager = manager;
        this.logger = LoggerFactory.getLogger(CartaoService.class);
    }

    @Scheduled(fixedDelayString = "${tempo.verificadordecartao}")
    @Transactional
    public void associa() {
        logger.info("[SCHEDULED] Verificando se existem propostas cadastradas...");

        TypedQuery<Proposta> propostas = manager.createNamedQuery("findPropostaByStatus", Proposta.class)
                .setParameter("statusAvaliacao", StatusAvaliacaoProposta.ELEGIVEL);

            propostas.getResultList().forEach(proposta -> {
            CartaoResponse cartaoResponse = integracaoCartao.pesquisaIdProposta(proposta.getId().longValue());

            if (proposta.verificarSeNaoExisteCartao()) {
                Assert.notNull(cartaoResponse, "O cartão não pode ser nulo");
                Cartao cartao = cartaoResponse.toCartao();
                manager.persist(cartao);
                proposta.incluirCartaoNaProposta(cartao);
                manager.merge(proposta);
                logger.info("CRIAÇÃO DE CARTÃO PARA PROPOSTA ELEGÍVEL- Cartão criado para proposta {}", proposta.getId());
            }
        });
    }
}
