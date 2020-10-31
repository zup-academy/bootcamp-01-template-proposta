package br.com.proposta.services;

import br.com.proposta.dtos.requests.SolicitacaoAnaliseRequest;
import br.com.proposta.dtos.responses.ResultadoAnaliseResponse;
import br.com.proposta.integracoes.IntegracaoApiAnalise;
import br.com.proposta.models.Enums.StatusAvaliacaoProposta;
import br.com.proposta.models.Proposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class AvaliaPropostaService {

    /* total de pontos = 6 */

    /* @complexidade - classe criada no projeto */
    private ResultadoAnaliseResponse resultadoAnaliseResponseJson;

    /* @complexidade - classe criada no projeto */
    private final IntegracaoApiAnalise integracaoApiAnalise;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public AvaliaPropostaService(IntegracaoApiAnalise integracaoApiAnalise, ResultadoAnaliseResponse resultadoAnaliseResponseJson) {
        this.integracaoApiAnalise = integracaoApiAnalise;
        this.resultadoAnaliseResponseJson = resultadoAnaliseResponseJson;
    }


    public StatusAvaliacaoProposta retornarAvaliacao(Proposta proposta) throws JsonProcessingException {

        /* @complexidade - try-catch */
        try{

            /* @complexidade - classe criada no projeto */
            resultadoAnaliseResponseJson = integracaoApiAnalise.avaliaproposta(new SolicitacaoAnaliseRequest(proposta)).getBody();


        }catch(FeignException e){

            /* @complexidade - classe criada no projeto + @complexidade - pacote externo utilizado no projeto */
            resultadoAnaliseResponseJson = new ObjectMapper().readValue(e.contentUTF8(), ResultadoAnaliseResponse.class);
        }


        logger.info("Proposta={} Status={} retornada da avaliação da API de Análise.",
                resultadoAnaliseResponseJson.getNome(), resultadoAnaliseResponseJson.getResultadoSolicitacao());


        return resultadoAnaliseResponseJson.retornaStatus();

    }
}
