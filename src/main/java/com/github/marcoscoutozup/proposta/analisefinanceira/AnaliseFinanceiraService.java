package com.github.marcoscoutozup.proposta.analisefinanceira;

import com.github.marcoscoutozup.proposta.proposta.Proposta;
import com.github.marcoscoutozup.proposta.proposta.enums.StatusDaProposta;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AnaliseFinanceiraService {

                //1
    private AnaliseFinanceira analiseFinanceira;
                //2
    private AnaliseFinanceiraRequest analiseFinanceiraRequest;
                //3
    private AnaliseFinanceiraResponse analiseFinanceiraResponse;

    private Logger logger;

    public AnaliseFinanceiraService(AnaliseFinanceira analiseFinanceira, Logger logger) {
        this.analiseFinanceira = analiseFinanceira;
        this.logger = logger;
    }
            //4
    public Proposta processarAnaliseFinanceiraDaProposta(Proposta proposta) {
        Assert.notNull(proposta, "A proposta avaliada não pode ser nula");
        logger.info("Processando a análise financeira de proposta: " + proposta.getId());
        analiseFinanceiraRequest = new AnaliseFinanceiraRequest(proposta);
        analiseFinanceiraResponse = analiseFinanceira.processaAnaliseFinanceira(analiseFinanceiraRequest);

        //5
        StatusDaProposta status = analiseFinanceiraResponse.getResultadoSolicitacao();
        proposta.modificarStatusDaProposta(status);
        return proposta;
    }
}
