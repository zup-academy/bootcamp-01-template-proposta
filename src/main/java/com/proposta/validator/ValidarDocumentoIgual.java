package com.proposta.validator;

import com.proposta.criacaoproposta.PropostaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class ValidarDocumentoIgual {

    @Autowired
    private EntityManager manager;

    public boolean validarDocumento(PropostaRequest request) {

        return manager.createQuery(
                "select p.documento from Proposta p where p.documento = :documento")
                .setParameter("documento", request.getDocumento())
                .getResultList().isEmpty();
    }
}
