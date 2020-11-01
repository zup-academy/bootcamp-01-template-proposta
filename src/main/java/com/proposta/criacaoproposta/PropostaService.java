package com.proposta.criacaoproposta;

import com.proposta.feign.ApiCartaoCliente;
import com.proposta.feign.AnalisePropostaCliente;
import com.proposta.feign.request.SolicitacaoCriarCartao;
import com.proposta.feign.response.ResultadoAnaliseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class PropostaService {

    @Autowired
    EntityManager manager;

    @Autowired
    private AnalisePropostaCliente analisePropostaCliente;

    @Autowired
    private ApiCartaoCliente apiCartaoCliente;

    public PropostaResponse cria(Proposta proposta) {

        manager.persist(proposta);

        ResultadoAnaliseResponse analiseResponse = analisePropostaCliente.solicitarAnalise(proposta.toAnalise());

        if (analiseResponse.getResultadoSolicitacao() == ResultadoAnaliseResponse.ResultadoSolicitacao.COM_RESTRICAO) {
            proposta.setStatus(StatusProposta.NAO_ELEGIVEL);
        } else if (analiseResponse.getResultadoSolicitacao() == ResultadoAnaliseResponse.ResultadoSolicitacao.SEM_RESTRICAO) {
            proposta.setStatus(StatusProposta.ELEGIVEL);
            apiCartaoCliente.solicitarCartao(new SolicitacaoCriarCartao(proposta.getDocumento(), proposta.getNome(), proposta.getId().toString()));
        }

        manager.merge(proposta);

        return new PropostaResponse(proposta.getId().toString());
    }
}
