package br.com.proposta.compartilhado;

import br.com.proposta.transferenciadados.requisicoes.RequisicaoSolicitarAnaliseDaProposta;
import br.com.proposta.transferenciadados.respostas.RespostaAnaliseDeProposta;
import br.com.proposta.integracoes.IntegracaoApiAnalise;
import br.com.proposta.entidades.Enums.StatusAvaliacaoProposta;
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

    /* @complexidade - classe criada no projeto */
    private RespostaAnaliseDeProposta respostaAnaliseDePropostaJson;

    /* @complexidade - classe criada no projeto */
    private final IntegracaoApiAnalise integracaoApiAnalise;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);


    public AvaliaProposta(IntegracaoApiAnalise integracaoApiAnalise, RespostaAnaliseDeProposta respostaAnaliseDePropostaJson) {
        this.integracaoApiAnalise = integracaoApiAnalise;
        this.respostaAnaliseDePropostaJson = respostaAnaliseDePropostaJson;
    }


    public StatusAvaliacaoProposta retornarAvaliacao(Proposta proposta) throws JsonProcessingException {

        /* @complexidade - try-catch */
        try{

            /* @complexidade - classe criada no projeto */
            respostaAnaliseDePropostaJson = integracaoApiAnalise.avaliaproposta(new RequisicaoSolicitarAnaliseDaProposta(proposta)).getBody();


        }catch(FeignException e){

            /* @complexidade - classe criada no projeto + @complexidade - pacote externo utilizado no projeto */
            respostaAnaliseDePropostaJson = new ObjectMapper().readValue(e.contentUTF8(), RespostaAnaliseDeProposta.class);
        }


        logger.info("Proposta={} Status={} retornada da avaliação da API de Análise.",
                respostaAnaliseDePropostaJson.getNome(), respostaAnaliseDePropostaJson.getResultadoSolicitacao());


        return respostaAnaliseDePropostaJson.retornaStatus();

    }
}
