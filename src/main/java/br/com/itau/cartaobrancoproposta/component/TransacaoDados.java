package br.com.itau.cartaobrancoproposta.component;

import br.com.itau.cartaobrancoproposta.model.Proposta;
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
    public void salva(@Valid Proposta proposta) {
        entityManager.persist(proposta);
    }

    @Transactional
    public void atualiza(@Valid Proposta proposta) {
        entityManager.merge(proposta);
    }

    @Transactional
    public <T> T busca(Class<?> classe, String id) {
        return (T) entityManager.find(classe, id);
    }
}
