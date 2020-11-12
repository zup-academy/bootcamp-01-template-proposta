package br.com.proposta.services;

import br.com.proposta.dtos.requests.AnaliseDaPropostaRequest;
import br.com.proposta.dtos.responses.AnaliseDaPropostaResponse;
import br.com.proposta.integracoes.IntegracaoApiAnalise;
import br.com.proposta.entidades.enums.StatusAvaliacaoProposta;
import br.com.proposta.entidades.Proposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class AvaliaProposta {

    /* total de pontos = 6 */

    /* @complexidade - acoplamento contextual */
    private final IntegracaoApiAnalise integracaoApiAnalise;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public AvaliaProposta(IntegracaoApiAnalise integracaoApiAnalise) {
        this.integracaoApiAnalise = integracaoApiAnalise;
    }


    public StatusAvaliacaoProposta retornarAvaliacao(Proposta proposta) throws JsonProcessingException {

        /* @complexidade - try-catch */
        try{

            /* @complexidade - classe criada no projeto */
           var respostaAnaliseDeProposta = integracaoApiAnalise.avaliaproposta(new AnaliseDaPropostaRequest(proposta)).getBody();

            logger.info("Proposta={} Status={} retornada da avaliação da API de Análise.",
                    respostaAnaliseDeProposta.getNome(), respostaAnaliseDeProposta.getResultadoSolicitacao());

            /* @complexidade - classe criada no projeto */
            return respostaAnaliseDeProposta.retornaStatus();


        }catch(FeignException e){


            /* @complexidade - classe criada no projeto + @complexidade - pacote externo utilizado no projeto */
            var respostaAnaliseDeProposta = new ObjectMapper().readValue(e.contentUTF8(), AnaliseDaPropostaResponse.class);

            logger.info("Proposta={} Status={} retornada da avaliação da API de Análise.",
                    respostaAnaliseDeProposta.getNome(), respostaAnaliseDeProposta.getResultadoSolicitacao());

            return respostaAnaliseDeProposta.retornaStatus();


        }
    }
}
