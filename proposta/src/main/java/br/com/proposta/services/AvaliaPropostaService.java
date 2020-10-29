package br.com.proposta.services;

import br.com.proposta.dtos.requests.SolicitacaoAnaliseRequest;
import br.com.proposta.dtos.responses.ResultadoAnaliseResponse;
import br.com.proposta.models.Enums.StatusAvaliacaoProposta;
import br.com.proposta.models.Proposta;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AvaliaPropostaService {

    @Autowired
    private IntegracaoPropostaService integracaoPropostaService;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public StatusAvaliacaoProposta retornarAvaliacao(Proposta proposta){

        ResultadoAnaliseResponse resultadoAnaliseResponseJson =
                    integracaoPropostaService.avaliaproposta(new SolicitacaoAnaliseRequest(proposta)).getBody();

        logger.info("Proposta={} Status={} status de avaliação de proposta retornado com sucesso!",
                resultadoAnaliseResponseJson.getNome(), resultadoAnaliseResponseJson.getResultadoSolicitacao());

        return resultadoAnaliseResponseJson.retornaStatus();

    }
}
