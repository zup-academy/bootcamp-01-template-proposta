package br.com.proposta.outrossistema;

import br.com.proposta.dtos.requests.SolicitacaoAnalise;
import br.com.proposta.dtos.responses.ResultadoAnalise;
import br.com.proposta.models.Enums.StatusAvaliacaoProposta;
import br.com.proposta.models.Proposta;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AvaliaProposta {

    @Autowired
    private IntegracaoProposta integracaoProposta;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    public StatusAvaliacaoProposta retornarAvaliacao(Proposta proposta){

        String resultadoAnalise =
                    integracaoProposta.avaliaproposta(new SolicitacaoAnalise(proposta));

        ResultadoAnalise resultadoAnaliseJson = new Gson().fromJson(resultadoAnalise, ResultadoAnalise.class);

        logger.info("Proposta={} Status={} status de avaliação de proposta retornado com sucesso!",
                resultadoAnaliseJson.getNome(), resultadoAnaliseJson.getResultadoSolicitacao());

        return resultadoAnaliseJson.retornaStatus();

    }
}
