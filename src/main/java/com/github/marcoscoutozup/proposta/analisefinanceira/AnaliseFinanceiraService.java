package com.github.marcoscoutozup.proposta.analisefinanceira;

import com.github.marcoscoutozup.proposta.proposta.Proposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AnaliseFinanceiraService {

                //1
    private AnaliseFinanceiraClient analiseFinanceiraClient;

    private Logger log = LoggerFactory.getLogger(AnaliseFinanceiraService.class);;

    public AnaliseFinanceiraService(AnaliseFinanceiraClient analiseFinanceiraClient) {
        this.analiseFinanceiraClient = analiseFinanceiraClient;
    }
                                                        //2
    public void processarAnaliseFinanceiraDaProposta(Proposta proposta) {
        Assert.notNull(proposta, "A proposta avaliada não pode ser nula");
        log.info("[ANALISE FINANCEIRA] Processando a análise financeira de proposta: {}", proposta.getId());

        //3
        AnaliseFinanceiraResponse analiseFinanceiraResponse =                       //4
                analiseFinanceiraClient.processaAnaliseFinanceira(proposta.toAnaliseFinanceiraRequest());

        Assert.notNull(analiseFinanceiraResponse, "A resposta da análise financeira não pode ser nula");
        proposta.modificarStatusDaProposta(analiseFinanceiraResponse.getResultadoSolicitacao().toStatusDaProposta());
    }
}
