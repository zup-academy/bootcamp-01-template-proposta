package br.com.itau.cartaobrancoproposta.validator;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Component
public class VerificaPropostaMesmoSolicitante {

    private final EntityManager manager;

    public VerificaPropostaMesmoSolicitante(EntityManager manager) {
        this.manager = manager;
    }

    public boolean estaValido(String documento) {
        Query query = manager.createQuery("select u from Proposta u where u.documento =: value");
        query.setParameter("value", documento);

        return query.getResultList().isEmpty();
    }
}
