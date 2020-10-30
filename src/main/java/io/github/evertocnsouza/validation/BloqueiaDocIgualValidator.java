package io.github.evertocnsouza.validation;

import io.github.evertocnsouza.rest.dto.request.PropostaRequest;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component public class BloqueiaDocIgualValidator {

    @PersistenceContext
    private EntityManager manager;

    public boolean estaValido(PropostaRequest request) {
        return manager.createQuery(
                "select p.documento from Proposta p where p.documento = :documento")
                .setParameter("documento", request.getDocumento())
                .getResultList().isEmpty();
    }

}
