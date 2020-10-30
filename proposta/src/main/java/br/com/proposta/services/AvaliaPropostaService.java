package br.com.proposta.services;

import br.com.proposta.dtos.requests.SolicitacaoAnaliseRequest;
import br.com.proposta.dtos.responses.ResultadoAnaliseResponse;
import br.com.proposta.models.Enums.StatusAvaliacaoProposta;
import br.com.proposta.models.Proposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AvaliaPropostaService {

    @Autowired
    private IntegracaoPropostaService integracaoPropostaService;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);



    public StatusAvaliacaoProposta retornarAvaliacao(Proposta proposta) throws JsonProcessingException {

        try{

            ResultadoAnaliseResponse resultadoAnaliseResponseJson =
                    integracaoPropostaService.avaliaproposta(new SolicitacaoAnaliseRequest(proposta)).getBody();

            logger.info("Proposta={} Status={} status de avaliação de proposta retornado sem restrição!",
                    resultadoAnaliseResponseJson.getNome(), resultadoAnaliseResponseJson.getResultadoSolicitacao());

            return resultadoAnaliseResponseJson.retornaStatus();

        }catch(FeignException e){

            ResultadoAnaliseResponse resultadoAnaliseResponseJson =
                    new ObjectMapper().readValue(e.contentUTF8(), ResultadoAnaliseResponse.class);

            logger.info("Proposta={} Status={} status de avaliação de proposta retornado com restrição!",
                    resultadoAnaliseResponseJson.getNome(), resultadoAnaliseResponseJson.getResultadoSolicitacao());

            return resultadoAnaliseResponseJson.retornaStatus();

        }
    }
}
