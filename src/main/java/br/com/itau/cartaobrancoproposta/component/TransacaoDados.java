package br.com.itau.cartaobrancoproposta.component;

import br.com.itau.cartaobrancoproposta.model.Cartao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;

@Component
public class TransacaoDados {

    private final EntityManager entityManager;

    public TransacaoDados(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public <T> void salva(@Valid T objeto) {
        entityManager.persist(objeto);
    }

    @Transactional
    public <T> void atualiza(@Valid T objeto) {
        entityManager.merge(objeto);
    }

    @Transactional
    public <T> T busca(Class<?> classe, String id) {
        return (T) entityManager.find(classe, id);
    }

    @Transactional
    public Cartao buscaPorNumeroDoCartao(String numeroCartao) {
        Query query = entityManager.createQuery("select u from Cartao u where u.numeroCartao =: value");
        query.setParameter("value", numeroCartao);

        if (query.getResultList().isEmpty()) { //1
            return null;
        }

        return (Cartao) query.getResultList().get(0);
    }
}
