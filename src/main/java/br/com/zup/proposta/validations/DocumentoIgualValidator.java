package br.com.zup.proposta.validations;

import br.com.zup.proposta.model.Proposta;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class DocumentoIgualValidator {

    @PersistenceContext
    EntityManager entityManager;

    public boolean existe(Proposta proposta){

        String documentoLimpo = proposta.obterCodificador()
                .decrypt(proposta.getDocumento());

        Query query = entityManager.createQuery("select 1 from " +
                "Proposta where documento =:documento");
        query.setParameter("documento", documentoLimpo);

        return !query.getResultList().isEmpty();

    }

}
