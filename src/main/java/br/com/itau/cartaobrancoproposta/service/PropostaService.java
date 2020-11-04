package br.com.itau.cartaobrancoproposta.service;

import br.com.itau.cartaobrancoproposta.client.AnaliseClient;
import br.com.itau.cartaobrancoproposta.error.ApiErrorException;
import br.com.itau.cartaobrancoproposta.model.Proposta;
import br.com.itau.cartaobrancoproposta.model.Solicitacao;
import br.com.itau.cartaobrancoproposta.model.SolicitacaoRequest;
import feign.FeignException;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PropostaService {

    Logger logger = LoggerFactory.getLogger(PropostaService.class);
//1
    @Autowired
    private AnaliseClient analiseClient;
//1
    public void buscaRestricaoProposta(Proposta proposta) {
        Solicitacao solicitacao; //1

        try { //1
            solicitacao = analiseClient.buscaAnaliseFinanceira(new SolicitacaoRequest(proposta));
        } catch (FeignException.UnprocessableEntity feignExcpetion) { //1
            logger.error("O documento {} não é elegível.", proposta.getDocumento());
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "O documento não é elegível"); //1
        } catch (RetryableException retryableException) { //1
            logger.error("Erro na requisição da análise do documento {}. Motivo: {}.", proposta.getDocumento(), retryableException.getMessage());
            throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um problema na requisição da análise");
        }

        proposta.validaRestricao(solicitacao.getResultadoSolicitacao());
    }
}
