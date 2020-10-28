package com.proposta.criacaoproposta;

import com.proposta.feign.AnalisePropostaCliente;
import com.proposta.validator.ValidarDocumentoIgual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class PropostaService {

    @Autowired
    EntityManager manager;

    @Autowired
    private AnalisePropostaCliente analisePropostaCliente;


    public PropostaResponse cria(Proposta proposta) {

        manager.persist(proposta);

        ResultadoAnaliseResponse response = analisePropostaCliente.solicitarAnalise(proposta.toAnalise());

        if (response.getResultadoSolicitacao() == ResultadoAnaliseResponse.ResultadoSolicitacao.COM_RESTRICAO) {
            proposta.setStatus(StatusProposta.NAO_ELEGIVEL);
        } else if (response.getResultadoSolicitacao() == ResultadoAnaliseResponse.ResultadoSolicitacao.SEM_RESTRICAO) {
            proposta.setStatus(StatusProposta.ELEGIVEL);
        }

        manager.merge(proposta);

        return new PropostaResponse(proposta.getId().toString());
    }
}
