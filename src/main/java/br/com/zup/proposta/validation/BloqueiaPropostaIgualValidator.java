package br.com.zup.proposta.validation;

import br.com.zup.proposta.novaproposta.NovaPropostaRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class BloqueiaPropostaIgualValidator {
    @PersistenceContext
    private EntityManager manager;

    public boolean isValid(NovaPropostaRequest request) {
        return manager.createQuery("select p.documento from Proposta p where p.documento = :documento")
                .setParameter("documento", request.getDocumento())
                .getResultList().isEmpty();
    }
}
