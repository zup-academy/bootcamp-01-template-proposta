package br.com.zup.proposta.validator;

import br.com.zup.proposta.dto.request.NovaPropostaRequest;
import br.com.zup.proposta.model.Proposta;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class BloqueiaDocIgualValidator {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean validaDocumento(NovaPropostaRequest request) {
        String documentoCriptografo = Proposta.criptografarDocumento(request.getDocumento());
        return entityManager.createQuery(
                "select p.documento from Proposta p where p.documento = :documento")
                .setParameter("documento", documentoCriptografo)
                .getResultList().isEmpty();
    }
}
