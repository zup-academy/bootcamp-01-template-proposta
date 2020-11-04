package br.com.itau.cartaobrancoproposta.component;

import br.com.itau.cartaobrancoproposta.client.CartaoClient;
import br.com.itau.cartaobrancoproposta.model.Proposta;
import br.com.itau.cartaobrancoproposta.model.Restricao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component
public class AssociaCartaoNaProposta {

    private final Logger logger = LoggerFactory.getLogger(AssociaCartaoNaProposta.class);
//1
    private final CartaoClient cartaoClient;
//1
    private final TransacaoDados transacaoDados;

    private final EntityManager entityManager;

    public AssociaCartaoNaProposta(CartaoClient cartaoClient, TransacaoDados transacaoDados, EntityManager entityManager) {
        this.cartaoClient = cartaoClient;
        this.transacaoDados = transacaoDados;
        this.entityManager = entityManager;
    }

    @Scheduled(fixedDelayString = "${associa.api.cartao.schedule}")
    public void associaCartao() {
        Query query = entityManager.createQuery("SELECT u FROM Proposta u");

        List<Proposta> propostaLista = query.getResultList(); //1

        propostaLista.forEach(proposta -> { //1
            if (proposta.getCartao().getIdCartao() == null && proposta.getRestricao() == Restricao.ELEGIVEL){ //1
                try { //1
                    proposta.verificaCartao(cartaoClient);
                    transacaoDados.atualiza(proposta);
                    logger.info("O cartão com final {} foi atrelado ao id={}", proposta.getCartao().getIdCartao().substring(24), proposta.getId());
                } catch (FeignException feignException) { //1
                    logger.error("Não foi possível atrelar o cartão a proposta id={}. Erro: {}", proposta.getId(), feignException.getLocalizedMessage());
                }
            }
        });
    }
}
