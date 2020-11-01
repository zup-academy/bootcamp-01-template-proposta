package br.com.zup.proposta.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class ExecutorTransacao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public <T> void salvaEComita (T objeto){
        entityManager.persist(objeto);
    }

    @Transactional
    public <T> void atualizaEComita(T objeto){
        entityManager.merge(objeto);
    }

}
