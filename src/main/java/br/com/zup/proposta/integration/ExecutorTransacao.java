package br.com.zup.proposta.integration;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class ExecutorTransacao {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public <T> T salva(T objeto) {
        manager.persist(objeto);
        return objeto;
    }

    @Transactional
    public <T> T atualiza(T objeto) {
        return manager.merge(objeto);
    }
}
