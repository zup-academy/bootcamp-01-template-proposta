package br.com.zup.proposta.validations;

import br.com.zup.proposta.dto.NovaPropostaRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class DocumentoIgualValidator {

    @PersistenceContext
    EntityManager entityManager;

    public boolean existe(NovaPropostaRequest request){

        Query query = entityManager.createQuery("select 1 from " +
                "Proposta where documento =:documento");
        query.setParameter("documento", request.getDocumento());

        return !query.getResultList().isEmpty();

    }

}
