package br.com.itau.cartaobrancoproposta.component;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
}
