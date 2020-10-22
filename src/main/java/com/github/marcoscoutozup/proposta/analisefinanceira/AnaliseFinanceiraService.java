package com.github.marcoscoutozup.proposta.analisefinanceira;

import com.github.marcoscoutozup.proposta.proposta.Proposta;
import com.github.marcoscoutozup.proposta.proposta.enums.StatusDaProposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AnaliseFinanceiraService {

                //1
    private AnaliseFinanceiraClient analiseFinanceiraClient;
                //2
    private AnaliseFinanceiraRequest analiseFinanceiraRequest;
                //3
    private AnaliseFinanceiraResponse analiseFinanceiraResponse;

    private Logger logger;

    public AnaliseFinanceiraService(AnaliseFinanceiraClient analiseFinanceiraClient) {
        this.analiseFinanceiraClient = analiseFinanceiraClient;
        this.logger = LoggerFactory.getLogger(AnaliseFinanceiraService.class);;
    }
            //4
    public void processarAnaliseFinanceiraDaProposta(Proposta proposta) {
        Assert.notNull(proposta, "A proposta avaliada não pode ser nula");
        logger.info("[ANALISE FINANCEIRA] Processando a análise financeira de proposta: {}", proposta.getId());
        analiseFinanceiraRequest = new AnaliseFinanceiraRequest(proposta);
        analiseFinanceiraResponse = analiseFinanceiraClient.processaAnaliseFinanceira(analiseFinanceiraRequest);

        //5
        StatusDaProposta status = analiseFinanceiraResponse.getResultadoSolicitacao();
        proposta.modificarStatusDaProposta(status);
    }
}
