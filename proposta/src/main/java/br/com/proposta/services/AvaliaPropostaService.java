package br.com.proposta.services;

import br.com.proposta.dtos.requests.SolicitacaoAnaliseRequest;
import br.com.proposta.dtos.responses.ResultadoAnaliseResponse;
import br.com.proposta.models.Enums.StatusAvaliacaoProposta;
import br.com.proposta.models.Proposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AvaliaPropostaService {


    private IntegracaoPropostaService integracaoPropostaService;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public AvaliaPropostaService(IntegracaoPropostaService integracaoPropostaService) {
        this.integracaoPropostaService = integracaoPropostaService;
    }

    public StatusAvaliacaoProposta retornarAvaliacao(Proposta proposta){

        ResultadoAnaliseResponse resultadoAnaliseResponseJson =
                    integracaoPropostaService.avaliaproposta(new SolicitacaoAnaliseRequest(proposta)).getBody();

        logger.info("Proposta={} Status={} status de avaliação de proposta retornado com sucesso!",
                resultadoAnaliseResponseJson.getNome(), resultadoAnaliseResponseJson.getResultadoSolicitacao());

        return resultadoAnaliseResponseJson.retornaStatus();

    }
}
